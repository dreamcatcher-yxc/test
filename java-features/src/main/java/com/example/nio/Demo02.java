package com.example.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class Demo02 {

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress("127.0.0.1", 8080));
        ByteBuffer allocate = ByteBuffer.allocate(64);
        allocate.put("hello world!".getBytes("UTF-8"));
        allocate.flip();
        while (allocate.hasRemaining()) {
            channel.write(allocate);
        }
//        channel.connect(new InetSocketAddress("148.251.188.73", 80));
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);

        while (true) {
            int readyChannels = selector.select();
            System.out.println("readyChannels: " + readyChannels);
            if(readyChannels == 0) continue;
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if(key.isReadable()) {
                    System.out.println("has channel readyable...");
                    SelectableChannel tc = key.channel();
                    if(tc instanceof SocketChannel) {
                        readDataFromChannel((SocketChannel) tc);
                    }
                }
            }
        }
    }

    public static void readDataFromChannel(SocketChannel channel) throws IOException {
        System.out.println("begin read data from channel...");
        // 分配大小为 48 字节的缓存空间
        ByteBuffer buf = ByteBuffer.allocate(48);
        // 读取到缓存
        int count = channel.read(buf);
        System.out.println("count is " + count);

        while (count != -1) {
            System.out.println("count: " + count);
            // 开始读取
            buf.flip();

            while (buf.hasRemaining()) {
                System.out.println((char)buf.get());
            }

            // 清空缓存
            buf.clear();
            // 继续读取
            count = channel.read(buf);
        }

        channel.close();
    }

}
