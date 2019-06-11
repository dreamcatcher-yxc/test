package com.example.netty.demo02;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Logger;

public class PlainNioClient {

    private static final Logger log = Logger.getLogger(PlainNioClient.class.getName());

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 8888);
        InputStream in = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String info;

        while ((info = br.readLine()) != null) {
            log.info("client received info: " + info);
        }

        socket.close();
    }

}
