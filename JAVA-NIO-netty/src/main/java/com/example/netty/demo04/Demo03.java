package com.example.netty.demo04;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.util.CharsetUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * 编码器、解码器测试
 */
public class Demo03 {

    @Test    //1
    public void testFramesDecoded() {
        ByteBuf buf = Unpooled.buffer();  //2

        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }

        // 创建一个引用副本, 但是索引和标记是分开的.
        ByteBuf input = buf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FrameChunkDecoder(3));  //3
        // 读取两个, 帧的大小没有查过 3 个字节, 写入没问题
        Assert.assertTrue(channel.writeInbound(input.readBytes(2)));  //4

        try {
            // 帧的大小为 4, 超过 3 个字节, 写入失败
            channel.writeInbound(input.readBytes(4)); //5
            Assert.fail();  //6
        } catch (TooLongFrameException e) {
            // expected
            e.printStackTrace();
        }

        // 如上已经读取了 input 中的前 6 个字节, 余下的 3 个读取出来写入, 刚好为 3, 可以写入
        Assert.assertTrue(channel.writeInbound(input.readBytes(3)));  //7

        Assert.assertTrue(channel.finish());  //8

        ByteBuf read = (ByteBuf) channel.readInbound();
        // 读取 buf 中的两个, channel 中写入的第一组相比, 相等
        Assert.assertEquals(buf.readSlice(2), read); //9
        read.release();

        read = (ByteBuf) channel.readInbound();
        // buf 读取完两个, 再跳过 4 个(因为中间的 4 个写入失败了), 读取最后三个和 channel 中读入的第二组帧比较, 也相等
        Assert.assertEquals(buf.skipBytes(4).readSlice(3), read);
        read.release();

        buf.release();
    }

    /**
     * 测试 channel 的 writeAndFlush 方法
     */
    @Test
    public void testWriteAndFlush() {
        // 直接创建一个内嵌通道
        Channel channel = new EmbeddedChannel();
        // 创建一个 ByteBuf 缓存数据
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello world!", CharsetUtil.UTF_8);
        // 直接写数据到客户端, 此方法会返回一个 ChannelFuture, 相当于 JS 完成时回调函数, 这样做的好处在于不会
        // 不会阻塞当前线程的继续执行
        ChannelFuture channelFuture = channel.writeAndFlush(byteBuf);
        // 添加监听器, 监听写事件完成结果
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.isSuccess()) {
                    System.out.println("write success!");
                } else {
                    System.out.println("write error!");
                    future.cause().printStackTrace();
                }
            }
        });
    }
}
