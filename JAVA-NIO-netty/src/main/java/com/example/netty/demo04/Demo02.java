package com.example.netty.demo04;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import org.junit.Assert;
import org.junit.Test;

/**
 * 编码、解码测试
 */
public class Demo02 {

    @Test   //1
    public void testEncoded() {
        ByteBuf buf = Unpooled.buffer();  //2

        for (int i = 1; i < 10; i++) {
            buf.writeInt(i * -1);
        }

        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());  //3
        Assert.assertTrue(channel.writeOutbound(buf)); //4

        Assert.assertTrue(channel.finish()); //5

        for (int i = 1; i < 10; i++) {
            int r = channel.readOutbound();
            Assert.assertEquals(i, r);  //6
        }

        // 已经循环 10 此了，循环完毕了, 所以再次读取返回的结果为 null.
        Assert.assertNull(channel.readOutbound());
    }


}
