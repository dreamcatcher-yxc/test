package com.example.netty.demo02;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PlainOIOServer {

    public void server(int port) throws IOException {
        final ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            final Socket socket = serverSocket.accept();

        }
    }
}
