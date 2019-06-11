package com.example.netty.demo02;

import com.sun.corba.se.spi.activation.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class NettyOioServer {

    public void server(int port) throws Exception {
        final ByteBuf buf = Unpooled.unreleasableBuffer(
                Unpooled.copiedBuffer("Hi!\r\n", Charset.forName("UTF-8")));
        EventLoopGroup group = new OioEventLoopGroup();
        try {
            // 创建一个 ServerBootstrap
            ServerBootstrap b = new ServerBootstrap();        //1

            // 使用 OioEventLoopGroup 允许阻塞模式（OIO）
            b.group(group)                                    //2
             .channel(OioServerSocketChannel.class)
             .localAddress(new InetSocketAddress(port))
             // 指定 ChannelInitializer 将给每个接受的连接调用
             .childHandler(new ChannelInitializer<SocketChannel>() {//3
                 @Override
                 public void initChannel(SocketChannel ch)
                     throws Exception {
                     // 添加的 ChannelHandler 拦截事件，并允许他们作出反应
                     ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {            //4
                         @Override
                         public void channelActive(ChannelHandlerContext ctx) throws Exception {
                             // 写信息到客户端，并添加 ChannelFutureListener 当一旦消息写入就关闭连接
                             ctx.writeAndFlush(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);//5
                         }
                     });
                 }
             });

            // 绑定服务器来接受连接
            ChannelFuture f = b.bind().sync();  //6
            f.channel().closeFuture().sync();
        } finally {
            // 释放所有资源
            group.shutdownGracefully().sync();        //7
        }
    }

    public static void main(String[] args) throws Exception {
        new NettyOioServer().server(8888);
    }
}