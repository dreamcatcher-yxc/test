package com.example.netty.demo06;

import io.netty.channel.ChannelHandler;

@ChannelHandler.Sharable
public class SpdyRequestHandler extends HttpRequestHandler {   //1
    @Override
    protected String getContent() {
        return "This content is transmitted via SPDY\r\n";  //2
    }
}