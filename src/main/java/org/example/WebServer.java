package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.function.*;

public class WebServer {
    private int port;
    private Map<String, Function<HttpExchange, String>> getHandlers = new HashMap<>();
    private Map<String, Function<HttpExchange, String>> postHandlers = new HashMap<>();
    private String staticFilesDirectory = "static";
    private boolean jsonResponse = false;

    public WebServer(int port) {
        this.port = port;
    }

    public void get(String path, Function<HttpExchange, String> handler) {
        getHandlers.put(path, handler);
    }

    public void post(String path, Function<HttpExchange, String> handler) {
        postHandlers.put(path, handler);
    }

    public void setStaticFilesDirectory(String directory) {
        staticFilesDirectory = directory;
    }

    public void setJsonResponse(boolean jsonResponse) {
        this.jsonResponse = jsonResponse;
    }

    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", this::handleRequest);
        server.setExecutor(null);
        server.start();
        System.out.println("Servidor web en ejecución en el puerto " + port);
    }

    private void handleRequest(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String response = null;

        if (exchange.getRequestMethod().equalsIgnoreCase("GET") && getHandlers.containsKey(path)) {
            response = getHandlers.get(path).apply(exchange);
        } else if (exchange.getRequestMethod().equalsIgnoreCase("POST") && postHandlers.containsKey(path)) {
            response = postHandlers.get(path).apply(exchange);
        } else if (path.startsWith("/static/")) {
            // Servir archivos estáticos
            serveStaticFile(exchange, path.substring(8)); // Eliminar "/static/"
            return;
        }

        if (jsonResponse) {
            exchange.getResponseHeaders().set("Content-Type", "application/json");
        }

        if (response != null) {
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            exchange.sendResponseHeaders(404, 0);
            exchange.close();
        }
    }

    //Entrega de archivos estáticos
    private void serveStaticFile(HttpExchange exchange, String path) throws IOException {
        File file = new File(staticFilesDirectory, path);
        if (file.exists() && !file.isDirectory()) {
            exchange.sendResponseHeaders(200, file.length());
            OutputStream os = exchange.getResponseBody();
            FileInputStream fs = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fs.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            fs.close();
            os.close();
        } else {
            exchange.sendResponseHeaders(404, 0);
            exchange.close();
        }
    }

    public static void main(String[] args) throws IOException {
        WebServer server = new WebServer(35000);

        // Registrar una ruta GET con una función lambda
        server.get("/hello", exchange -> "Hello, World!");

        // Registrar una ruta POST con una función lambda
        server.post("/echo", exchange -> {
            String requestBody = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))
                    .lines().reduce("", (accumulator, line) -> accumulator + line);
            return "Echo: " + requestBody;
        });

        // Configurar el directorio de archivos estáticos
        server.setStaticFilesDirectory("public");
        https://github.com/cristian-ruiz-s-ing/AREP-Microframeworks.git
        // Cambiar el tipo de respuesta a JSON
        server.setJsonResponse(true);

        // Iniciar el servidor
        server.start();
    }
}
