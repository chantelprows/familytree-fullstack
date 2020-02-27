package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.nio.file.*;

import java.io.IOException;

public class DefaultHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                String urlPath = exchange.getRequestURI().getPath().toString();
                if (urlPath.isEmpty() || urlPath.equals("/")) {
                    urlPath = "/index.html";
                }

                String filePathStr = "API" + urlPath;

                Path filePath = FileSystems.getDefault().getPath(filePathStr);
                if (!Files.exists(filePath)) {
                    throw new FileNotFoundException();
                }

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                Files.copy(filePath, exchange.getResponseBody());

                exchange.getResponseBody().close();

                success = true;
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (FileNotFoundException e) {
            exchange.sendResponseHeaders(404, 0);
            String filepathStr = "API/HTML/404.html";
            Path filePath = FileSystems.getDefault().getPath(filepathStr);
            Files.copy(filePath, exchange.getResponseBody());
            exchange.getResponseBody().close();
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }
    }
}
