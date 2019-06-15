package com.example.netty.demo08;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.URI;

/**
 * Bootstrap、ServerBootstrap 测试
 */
public class Demo01 {

    /**
     * 客户端模拟请求
     */
    @Test
    public void test01() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println("receive data....");
                    }

                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                        System.out.println("throws exception....");
                    }
                })
                .connect(new InetSocketAddress("www.manning.com", 80))
                .addListener(future -> {
                    if(future.isSuccess()) {
                        System.out.println("Connection established");
                    } else {
                        System.err.println("Connection attempt failed");
                        future.cause().printStackTrace();
                    }
                 });
    }

    /**
     * Netty 作为 Http 请求客户端
     * @throws Exception
     */
    @Test
    public void test02() throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group)
                .remoteAddress(new InetSocketAddress("www.baidu.com", 80))
                //长连接
                .option(ChannelOption.SO_KEEPALIVE, true)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    protected void initChannel(Channel channel) throws Exception {
                        //包含编码器和解码器
                        channel.pipeline().addLast(new HttpClientCodec());
                        //聚合
                        channel.pipeline().addLast(new HttpObjectAggregator(1024 * 10 * 1024));
                        //解压
                        channel.pipeline().addLast(new HttpContentDecompressor());
                        //客户端处理
                        channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                FullHttpResponse response = (FullHttpResponse) msg;

                                ByteBuf content = response.content();
                                HttpHeaders headers = response.headers();

                                System.out.println("content:" + System.getProperty("line.separator") + content.toString(CharsetUtil.UTF_8));
                                System.out.println("headers:" + System.getProperty("line.separator") + headers.toString());
                            }

                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                URI url = new URI("/");
                                String meg = "hello";

                                //配置HttpRequest的请求数据和一些配置信息
                                FullHttpRequest request = new DefaultFullHttpRequest(
                                        HttpVersion.HTTP_1_0, HttpMethod.GET, url.toASCIIString(), Unpooled.wrappedBuffer(meg.getBytes("UTF-8")));

                                request.headers()
                                        .set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8")
                                        //开启长连接
                                        .set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE)
                                        //设置传递请求内容的长度
                                        .set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());

                                //发送数据
                                ctx.writeAndFlush(request);
                            }
                        });
                    }
                });

        ChannelFuture channelFuture = bootstrap.connect().sync();
        channelFuture.channel().closeFuture().sync();
    }

    @Test
    public void test03() {

    }
}
