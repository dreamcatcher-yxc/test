package com.example.netty.demo02;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;
import java.util.logging.MemoryHandler;

public class PlainNioServer {
    private static final Logger log = Logger.getLogger(PlainNioServer.class.getName());

    public void serve(int port) throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        ServerSocket ss = serverChannel.socket();
        InetSocketAddress address = new InetSocketAddress(port);
        // 绑定服务器到制定端口
        ss.bind(address);                                            //1
        // 打开 selector 处理 channel
        Selector selector = Selector.open();                        //2
        // 注册 ServerSocket 到 ServerSocket ，并指定这是专门意接受 连接。
        serverChannel.register(selector, SelectionKey.OP_ACCEPT, "SERVER_CHANNEL_1");    //3
        final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());
        log.info("开始监听客户端连接...");

        for (;;) {
            try {
                // 等待新的事件来处理。这将阻塞，直到一个事件是传入。
                selector.select();                                    //4
            } catch (IOException ex) {
                ex.printStackTrace();
                // handle exception
                break;
            }

            // 从收到的所有事件中 获取 SelectionKey 实例。
            Set<SelectionKey> readyKeys = selector.selectedKeys();    //5
            Iterator<SelectionKey> iterator = readyKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                try {
                    // 检查该事件是一个新的连接准备好接受。
                    if (key.isAcceptable()) {                //6
                        log.info(key.attachment() + ": 连接准备就绪...");
                        ServerSocketChannel server =
                                (ServerSocketChannel)key.channel();
                        SocketChannel client = server.accept();
                        client.configureBlocking(false);

                        // 接受客户端，并用 selector 进行注册。
                        HashMap<String, Object> attachObj = new HashMap<>();
                        attachObj.put("buff", msg.duplicate()); // 赋值一份缓冲区消息
                        attachObj.put("name", "CLIENT_CHANNEL_1");

                        client.register(selector, SelectionKey.OP_WRITE |
                                SelectionKey.OP_READ, attachObj);    //7
                        log.info(key.attachment() + ": Accepted connection from " + client);
                    }

                    // 检查 socket 是否准备好写数据。
                    if (key.isWritable()) {                //8
                        HashMap<String, Object> attachObj = (HashMap<String, Object>) key.attachment();
                        String name = (String) attachObj.get("name");

                        log.info(name + ": 数据写入准备就绪...");
                        SocketChannel client =
                                (SocketChannel)key.channel();

                        ByteBuffer buffer =
                                (ByteBuffer)attachObj.get("buff");

                        log.info(name + ": 写入开始...");

                        while (buffer.hasRemaining()) {
                            // 将数据写入到所连接的客户端。如果网络饱和，连接是可写的，那么这个循环将写入数据，直到该缓冲区是空的。
                            if (client.write(buffer) == 0) {        //9
                                break;
                            }
                        }

                        log.info(name + ": 写入完成...");
                        log.info(name + ": 关闭写入通道...");
                        // 关闭连接。
                        client.close();                    //10
                    }
                } catch (IOException ex) {
                    key.cancel();

                    try {
                        key.channel().close();
                    } catch (IOException cex) {
                        // 在关闭时忽略
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new PlainNioServer().serve(8888);
    }
}