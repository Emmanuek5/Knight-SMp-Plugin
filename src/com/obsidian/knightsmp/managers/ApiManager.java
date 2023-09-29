package com.obsidian.knightsmp.managers;

import com.obsidian.knightsmp.KnightSmp;
import com.obsidian.knightsmp.managers.HttpHandlers.PlayerHandler;
import com.obsidian.knightsmp.server.WebServerHandler;
import com.sun.net.httpserver.HttpHandler;

import java.util.ArrayList;
import java.util.List;

public class ApiManager {
    private final WebServerHandler webServerHandler;
    private static List<RouteHandler> routeHandlers;
    private ArrayList<String> routes;


    public ApiManager(WebServerHandler webServerHandler) {
        this.webServerHandler = webServerHandler;
        this.routeHandlers = new ArrayList<>();
        this.routes = new ArrayList<>(); // Initialize the routes ArrayList here
        initializeRouteHandlers();
    }

    private void initializeRouteHandlers() {
        // Add your route handler instances to the list here
        routeHandlers.add(new PlayerHandler());
        // Add more route handler instances as needed
    }

    public void registerRoutes() {
        for (RouteHandler handlerInstance : routeHandlers) {
            routes.add(handlerInstance.getRoute());
            webServerHandler.addRoute(handlerInstance.getRoute(), handlerInstance.getMethod(), (HttpHandler) handlerInstance);
            KnightSmp.sendMessage("Registered route: " + handlerInstance.getRoute());
        }
    }

    public ArrayList<String> getRoutes() {
        return routes;
    }
}
