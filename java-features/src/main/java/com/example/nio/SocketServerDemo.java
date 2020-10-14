package com.example.nio;

import com.alibaba.fastjson.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerDemo {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("server running, port is 8080...");

        while (true) {
            Socket accept = serverSocket.accept();
            System.out.println("accept connect...");
            InputStream in = accept.getInputStream();
            int len;
            byte[] buf = new byte[1024];
            StringBuffer sb = new StringBuffer();

            while ((len = in.read(buf)) > 0) {
                sb.append(new String(buf, 0, len, "UTF-8"));
            }

            String content = sb.toString();
            System.out.println("accept data is: " + content);

            if("close".equals(content)) {
                in.close();
                accept.close();
                break;
            }

            OutputStream out = accept.getOutputStream();
            out.write(String.valueOf(System.currentTimeMillis()).getBytes("UTF-8"));
            out.close();
            accept.close();
        }

        serverSocket.close();
    }

}
