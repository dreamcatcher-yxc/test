package com.example.java.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Pipe;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ChannelDemo02 {

    @Test
    public void testSelector01() throws IOException {

    }

    /**
     * 使用 TCP 方式连接指定主机和端口, 默认为阻塞式
     * @throws Exception
     */
    @Test
    public void testSocketChannel() throws Exception {
        // 创建连接
        SocketChannel channel = SocketChannel.open();
        // 指定连接地址(包含主机名和端口)
        channel.connect(new InetSocketAddress("127.0.0.1", 8888));
        // 创建大小为 1024 个字节的缓冲区
        ByteBuffer buff = ByteBuffer.allocate(1024);
        while (channel.read(buff) != -1) {
            buff.flip();
            byte[] tbs = new byte[buff.limit()];
            buff.get(tbs);
            System.out.print(new String(tbs));
            buff.clear();
        }
        channel.close();
    }

    /**
     * 使用 ServerSocketChannel 监听本地端口。
     * @throws Exception
     */
    @Test
    public void testServerSocketChannel01() throws Exception {
        // 创建服务端 socket 通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 绑定本地 8888 端口
        serverSocketChannel.socket().bind(new InetSocketAddress(8888));
        // 配置为非阻塞式
        serverSocketChannel.configureBlocking(false);
        ByteBuffer buff = ByteBuffer.allocate(1024);

        while(true) {
            // 由于配置的是非阻塞式, 此处不会阻塞，如果没有新的连接接入，会返回 null
            SocketChannel socketChannel = serverSocketChannel.accept();
            if(socketChannel != null) {
                System.out.println("new connected...");
                // do something with socketChannel
                String serverResponseMsg = "server current time millis is " + System.currentTimeMillis();
                buff.put(serverResponseMsg.getBytes());
                buff.flip();
                // 将缓存中的数据写入通道
                socketChannel.write(buff);
                // 关闭通道，数据将被发出
                socketChannel.close();
                System.out.println("server response message send successful!!! response message is: " + serverResponseMsg);
                buff.clear();
            }
        }
    }

    /**
     * 基于 UDP 协议接收数据
     * @throws Exception
     */
    @Test
    public void testDatagramChannel01() throws Exception {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.socket().bind(new InetSocketAddress(9999));
        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        System.out.println("等待接收数据...");
        datagramChannel.receive(buf);
        buf.flip();
        byte[] tbs = new byte[buf.limit()];
        buf.get(tbs);
        System.out.println("receive msg is: " + new String(tbs));
        datagramChannel.close();
    }

    /**
     * 基于 UDP 协议发送数据
     * @throws Exception
     */
    @Test
    public void testDatagramChannel02() throws Exception {
        DatagramChannel datagramChannel = DatagramChannel.open();
        String newData = "server current time millis is " + System.currentTimeMillis();
        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        buf.put(newData.getBytes());
        buf.flip();
        datagramChannel.send(buf, new InetSocketAddress("localhost", 9999));
        System.out.println("数据发送成功....");
        datagramChannel.close();
    }

    /**
     * 使用管道的方式单向发送数据
     * @throws Exception
     */
    @Test
    public void testPipe() throws Exception {
        Pipe pipe = Pipe.open();
        ByteBuffer buf01 = ByteBuffer.allocate(48);
        buf01.clear();
        buf01.put("hello world!".getBytes());
        buf01.flip();
        // 向 pipe 中写数据
        pipe.sink().write(buf01);

        ByteBuffer buf02 = ByteBuffer.allocate(48);
        buf02.clear();
        // 在 pipe 中读取数据
        pipe.source().read(buf02);
        buf02.flip();
        byte[] tbs = new byte[buf02.limit()];
        buf02.get(tbs);
        System.out.println(new String(tbs));
    }
}
