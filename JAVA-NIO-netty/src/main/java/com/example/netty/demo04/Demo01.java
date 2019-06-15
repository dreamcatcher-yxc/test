package com.example.netty.demo04;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * 自定义编码器、解码器测试
 */
public class Demo01 {

    @Test    //1
    public void testFramesDecoded() {
        ByteBuf buf = Unpooled.buffer(); //2

        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }

        ByteBuf input = buf.duplicate();

        // new FixedLengthFrameDecoder(3) 标识在 channel 读取 inbound 数据解码的时候, 需要有 3 传入数据才会被解码, 否则解码结果为 null。
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3)); //3

        // input.readBytes(2) 只读取了两个字节, 使用 writeInbound 读入 channel, 则解码结果为 null, 未添加任何数据到 inbound 的缓冲区中,
        // 故返回 false.
        Assert.assertFalse(channel.writeInbound(input.readBytes(2))); //4
        // input.readBytes(2) 只读取了两个字节, 使用 writeInbound 读入 channel, 则解码结果不为 null, 未添加任何数据到 inbound 的缓冲区中,
        // 故返回 true.
        Assert.assertTrue(channel.writeInbound(input.readBytes(7)));
        Assert.assertTrue(channel.finish());  //5

        // 读取第一组
        ByteBuf read = (ByteBuf) channel.readInbound();
        // 读取前三个
        ByteBuf read2 = buf.readSlice(3);
//        System.out.println(Arrays.toString(read.array()));
//        System.out.println(Arrays.toString(read2.array()));

        Assert.assertEquals(read2, read);
        read.release();

        // 第二组
        read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        // 第三组, 因为传入了 7 个字节, 每 3 个一组, 第三组不足 3 个, 为 null.
        read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        Assert.assertNull(channel.readInbound());
        buf.release();
    }


    @Test
    public void testFramesDecoded2() {
        ByteBuf buf = Unpooled.buffer();

        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }

        ByteBuf input = buf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        Assert.assertFalse(channel.writeInbound(input.readBytes(2)));
        Assert.assertTrue(channel.writeInbound(input.readBytes(7)));

        Assert.assertTrue(channel.finish());
        ByteBuf read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        Assert.assertNull(channel.readInbound());
        buf.release();
    }


}
