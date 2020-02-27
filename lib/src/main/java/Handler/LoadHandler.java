package Handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import DataTransfer.LoadRequest;
import DataTransfer.LoadResult;
import Model.Event;
import Model.Person;
import Model.User;
import Service.Loader;

public class LoadHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream reqBody = exchange.getRequestBody();

                InputStreamReader reader = new InputStreamReader(reqBody);
                Gson gson = new Gson();

                JsonObject jsonObj = gson.fromJson(reader, JsonObject.class);

                User[] users = gson.fromJson(jsonObj.getAsJsonArray("users"), User[].class);
                Person[] persons = gson.fromJson(jsonObj.getAsJsonArray("persons"), Person[].class);
                Event[] events = gson.fromJson(jsonObj.getAsJsonArray("events"), Event[].class);

                LoadRequest r = new LoadRequest();
                Loader loader = new Loader();
                LoadResult lr;

                r.setUsers(users);
                r.setPersons(persons);
                r.setEvents(events);

                lr = loader.load(r);

                if (!(lr.getErrorMessage() == null)) {
                    lr.setSuccessMessage(null);
                }

                String respData = gson.toJson(lr);

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                OutputStream respBody = exchange.getResponseBody();
                writeString(respData, respBody);
                respBody.close();

                success = true;
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

    private String readString (InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
