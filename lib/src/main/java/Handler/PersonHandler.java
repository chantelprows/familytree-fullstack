package Handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.sql.SQLException;

import DataAccess.AuthTokenDao;
import DataTransfer.AllPersonRequest;
import DataTransfer.AllPersonResult;
import DataTransfer.SinglePersonRequest;
import DataTransfer.SinglePersonResult;
import Service.AllPerson;
import Service.SinglePerson;

public class PersonHandler implements HttpHandler {
     boolean success = false;

     @Override
     public void handle(HttpExchange exchange) throws IOException {
         try {
             if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                 Headers reqHeaders = exchange.getRequestHeaders();
                 String urlPath = exchange.getRequestURI().getPath().toString();
                 if (reqHeaders.containsKey("Authorization")) {
                     String authToken = reqHeaders.getFirst("Authorization");
                     AuthTokenDao ad = new AuthTokenDao();
                     SinglePersonResult sr = new SinglePersonResult();
                     AllPersonResult ar;
                     Gson gson = new Gson();
                     String respData;

                     try {
                         ad.getAuthToken(authToken);
                     }
                     catch (SQLException e) {
                         sr.setErrorMessage("invalid auth token");
                     }

                     String arr[] = urlPath.split("/");
                     if (arr.length > 2) {

                         SinglePersonRequest r = new SinglePersonRequest();
                         r.setPersonID(arr[2]);
                         r.setAuthToken(authToken);

                         SinglePerson sp = new SinglePerson();

                         sr = sp.returnPerson(r);
                         if (!(sr.getErrorMessage() == null)) {
                             sr.setSpouse(null);
                             sr.setMother(null);
                             sr.setFather(null);
                             sr.setGender(null);
                             sr.setLastName(null);
                             sr.setFirstName(null);
                             sr.setPersonID(null);
                             sr.setDescendant(null);
                         }

                         respData = gson.toJson(sr);

                     }
                     else {
                         AllPersonRequest r = new AllPersonRequest();
                         r.setAuthToken(authToken);

                         AllPerson ap = new AllPerson();

                         ar = ap.getAllPeople(r);

                         if (!(ar.getErrorMessage() == null)) {
                             ar.setPeople(null);
                         }

                         respData = gson.toJson(ar);
                     }

                     exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                     OutputStream respBody = exchange.getResponseBody();
                     writeString(respData, respBody);
                     respBody.close();

                     success = true;
                 }
             }
             if (!success) {
                 exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                 exchange.getResponseBody().close();
             }
         } catch (IOException e) {
             exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
             exchange.getResponseBody().close();
             e.printStackTrace();
         }
     }

     private void writeString(String str, OutputStream os) throws IOException {
         OutputStreamWriter sw = new OutputStreamWriter(os);
         sw.write(str);
         sw.flush();
     }
}

