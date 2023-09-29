package com.obsidian.knightsmp.server;

import com.obsidian.knightsmp.KnightSmp;
import com.obsidian.knightsmp.managers.HttpHandlers.PlayerHandler;
import com.obsidian.knightsmp.server.HttpMethod;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class WebServerHandler {
    private final HttpServer server;

    public WebServerHandler(HttpServer server) {
        this.server = server;
        new PlayerHandler();
    }

    public void addRoute(String path, HttpMethod method, HttpHandler handler) {
        server.createContext(path, exchange -> {
            if (exchange.getRequestMethod().equalsIgnoreCase(method.name())) {
                handler.handle(exchange);
            } else {
                String response = "Unsupported method: " + exchange.getRequestMethod();
                sendResponse(exchange, response, 405); // Method Not Allowed
            }
        });
    }

    public void handleDefault(HttpMethod method, HttpHandler handler) {
        server.createContext("/", exchange -> {
            if (exchange.getRequestMethod().equalsIgnoreCase(method.name())) {
                handler.handle(exchange);
            } else {
                String response = "Unsupported method: " + exchange.getRequestMethod();
                sendResponse(exchange, response, 405); // Method Not Allowed
            }
        });
    }

    private void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
