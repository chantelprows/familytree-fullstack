package Handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.*;

import com.google.gson.*;
import com.sun.net.httpserver.*;

import DataTransfer.RegisterRequest;
import DataTransfer.RegisterResult;
import Service.Registration;

public class RegisterHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream reqBody = exchange.getRequestBody();

                String reqData = readString(reqBody);
                System.out.println(reqData);

                Gson gson = new Gson();
                RegisterRequest r;
                r = gson.fromJson(reqData, RegisterRequest.class);

                Registration registration = new Registration();
                RegisterResult rr;

                rr = registration.createUser(r);
                if (!(rr.getErrorMessage() == null)) {
                    rr.setPersonID(null);
                    rr.setUsername(null);
                    rr.setAuthToken(null);
                }
                String respData = gson.toJson(rr);

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
