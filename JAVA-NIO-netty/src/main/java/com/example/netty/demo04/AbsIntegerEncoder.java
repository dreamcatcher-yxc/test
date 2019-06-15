package com.example.netty.demo04;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        // 可读数据长度高于 4 个, 则读取数据
        while (in.readableBytes() >= 4) {
            // 取绝对值
            int value = Math.abs(in.readInt());//3
            out.add(value);  //4
        }
    }
}