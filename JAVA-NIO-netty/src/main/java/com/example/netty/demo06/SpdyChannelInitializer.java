package com.example.netty.demo06;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import java.io.File;

public class SpdyChannelInitializer extends ChannelInitializer<SocketChannel> {  //1

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        SelfSignedCertificate cert = new SelfSignedCertificate();
        File privateKey = cert.privateKey();
        File certificate = cert.certificate();
        SslContext sslCtx = SslContextBuilder.forServer(certificate, privateKey).build();
        pipeline.addLast("SslHandler", sslCtx.newHandler(ch.alloc()));
        pipeline.addLast("chooser", new DefaultSpdyOrHttpChooser(1024 * 1024, 1024 * 1024));
    }
}