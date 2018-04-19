package com.example.netty.demo02;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * 使用 ServerSocket 实现的尖端服务器.
 */
public class ServerSocketImplServer {

    private final String responseHeader = "Cache-Control:max-age=0, private, must-revalidate\n" +
            "Connection:keep-alive\n" +
            "Content-Type:text/html; charset=utf-8\n";

    public void server(int port) throws IOException {
        final ServerSocket serverSocket = new ServerSocket(port);

        try {
            while (true) {
                // 等待客户端连接
                final Socket clientSocket = serverSocket.accept();
                System.out.println("Accept connection from" + clientSocket.getInetAddress().getHostName());

                new Thread(new Runnable()  {
                    public void run() {
                        OutputStream out;
                        InputStream in;

                        try {
                            in = clientSocket.getInputStream();
                            out = clientSocket.getOutputStream();
                            byte[] buff = new byte[1024];
                            int len;
                            StringBuffer sb = new StringBuffer();

                            while ((len = in.read(buff)) >= 0) {
                                sb.append(new String(buff, 0, len));
                            }

                            System.out.println("accept content is: ");
                            System.out.println(sb.toString());

                            out.write((responseHeader + "\r\n<b>hello world!</b>").getBytes(Charset.forName("UTF-8")));

                            out.flush();
                            in.close();
                            out.close();
                            clientSocket.close(); //5

                        } catch (IOException e) {
                            e.printStackTrace();
                            try {
                                clientSocket.close();
                            } catch (IOException ex) {
                            }
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new ServerSocketImplServer().server(8080);
    }
}
