package com.obsidian.knightsmp.managers.HttpHandlers;

import com.obsidian.knightsmp.KnightSmp;
import com.obsidian.knightsmp.managers.ApiManager;
import com.obsidian.knightsmp.managers.RouteHandler;
import com.obsidian.knightsmp.server.HttpMethod;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DefaultHttpHandler implements RouteHandler, HttpHandler {
    @Override
    public String getRoute() {
        return null;
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.GET;
    }

    @Override
    public void handleRequest(HttpExchange exchange) throws IOException {
        ArrayList routes = KnightSmp.apiManager.getRoutes();
        String response = "Available routes:\n";
        for (int i = 0; i < routes.size(); i++) {
            response += routes.get(i) + "\n";
        }
        sendResponse(exchange, response, 200);

    }
    private void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void handle(HttpExchange exchange) throws IOException {
      handleRequest(exchange);
    }
}
