package com.obsidian.knightsmp.managers;

import com.obsidian.knightsmp.server.HttpMethod;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public interface RouteHandler {
    String getRoute(); // Returns the route URL
    HttpMethod getMethod(); // Returns the HTTP method (GET, POST, etc.)
    void handleRequest(HttpExchange exchange) throws IOException; // Handles the incoming request
}
