package Handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import DataTransfer.FillRequest;
import DataTransfer.FillResult;
import Service.Filler;

public class FillHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                String urlPath = exchange.getRequestURI().getPath().toString();

                String arr[] = urlPath.split("/");

                FillRequest r = new FillRequest();
                r.setUsername(arr[2]);

                if (arr.length > 3) {
                    r.setGenerations(Integer.parseInt(arr[3]));
                }
                else {
                    r.setGenerations(4);
                }

                Filler filler = new Filler();
                FillResult fr;

                fr = filler.fill(r);
                if (!(fr.getErrorMessage() == null)) {
                    fr.setSuccessMessage(null);
                }
                Gson gson = new Gson();
                String respData = gson.toJson(fr);

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

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
