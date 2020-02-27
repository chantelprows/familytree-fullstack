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
import DataTransfer.AllEventRequest;
import DataTransfer.AllEventResult;
import DataTransfer.SingleEventRequest;
import DataTransfer.SingleEventResult;
import Service.AllEvent;
import Service.SingleEvent;

public class EventHandler implements HttpHandler {

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
                    SingleEventResult sr = new SingleEventResult();
                    AllEventResult aer;
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
                        SingleEventRequest r = new SingleEventRequest();
                        r.setAuthToken(authToken);
                        r.setEventID(arr[2]);

                        SingleEvent se = new SingleEvent();

                        sr = se.returnEvent(r);
                        if (!(sr.getErrorMessage() == null)) {
                            sr.setDescendant(null);
                            sr.setYear(null);
                            sr.setEventType(null);
                            sr.setCity(null);
                            sr.setCountry(null);
                            sr.setLongitude(0);
                            sr.setLatitude(0);
                            sr.setPersonID(null);
                            sr.setEventID(null);
                        }
                        respData = gson.toJson(sr);
                    }
                    else {
                        AllEventRequest r = new AllEventRequest();
                        r.setAuthToken(authToken);

                        AllEvent ae = new AllEvent();

                        aer = ae.returnAllEvents(r);
                        if (!(aer.getErrorMessage() == null)) {
                            aer.setEvents(null);
                        }
                        respData = gson.toJson(aer);
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
