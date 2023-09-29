package com.obsidian.knightsmp.server;

import com.obsidian.knightsmp.KnightSmp;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WebServer {
    private HttpServer server;

    public WebServer(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
    }

    public void start() {
        server.start();
   KnightSmp.sendMessage("Server started on port " + server.getAddress().getPort());
    }

    public HttpServer getServer() {
        return server;
    }
}
