package com.example.netty.demo05;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 安装 Handler
 */
public class ChatServerInitializer extends ChannelInitializer<Channel> {    //1
    private final ChannelGroup group;

    public ChatServerInitializer(ChannelGroup group) {
        this.group = group;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {            //2
        ChannelPipeline pipeline = ch.pipeline();
        // http 解码服务
        pipeline.addLast(new HttpServerCodec());
        // 由于 NIO 中数据都是一部分一部分传递的, 一个完整的 htt 请求可能会被分开接收到, 此 handler
        // 用于将这些分开的 http 请求组合成一个完整的 http 信息。
        pipeline.addLast(new HttpObjectAggregator(64 * 1024)); //
        // 允许返回大数据
        pipeline.addLast(new ChunkedWriteHandler());
        // 处理 websocket 请求, 如果发现是 websocket 会保留消息, 传递给下一个 Handler
        pipeline.addLast(new HttpRequestHandler("/ws"));
        // webSocket 协议处理 Handler
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        // websocket 文本发送, 需要持有 channelGroup, 因为消息需要群发.
        pipeline.addLast(new TextWebSocketFrameHandler(group));
    }
}