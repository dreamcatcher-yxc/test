package com.example.netty.demo05;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.util.Arrays;

/**
 * ByteBuf 测试
 */
public class Demo01 {

    /**
     * ByteBuf 测试
     */
    @Test
    public void test01() {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello world!", CharsetUtil.UTF_8);

        if(byteBuf.hasArray()) {
            int offset = byteBuf.arrayOffset() + byteBuf.readerIndex();
            int length = byteBuf.readableBytes();
            System.out.println("offset: " + offset + ", length: " + length);

            for(int i = offset; i < length; i++) {
                System.out.println((char)byteBuf.getByte(i));
            }
        }
    }

    /**
     * 测试 DirectBuf
     */
    @Test
    public void test02() {
        ByteBuf directBuf = Unpooled.directBuffer(20);
        directBuf.writeBytes("yangxiuchu".getBytes());

        // 检查 ByteBuf 是否由数, 组支撑。如果不是，则这是一个直接缓冲区。
        if(!directBuf.hasArray()) {
            // 可读取的字节数
            int length = directBuf.readableBytes();
            byte[] array = new byte[length];
            // 分配一个新的数组来保存具有该长度的字节数据
            directBuf.getBytes(directBuf.readerIndex(), array);
            System.out.println(Arrays.toString(array));
        }
    }

    /**
     * 随机访问 ByteBuf 中的数据
     */
    @Test
    public void test03() {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello world!", CharsetUtil.UTF_8);

        // getter、setter 方法并不会修改 readIndex、writeIndex 的值
        for (int i = 0; i < byteBuf.capacity(); i++) {
            byte b = byteBuf.getByte(i);
            System.out.print((char)b);
        }
    }

    /**
     * 测试 ByteBufAllocator
     */
    @Test
    public void test04() {
        Channel channel = new EmbeddedChannel();
        ByteBufAllocator alloc = channel.alloc();
    }

    /**
     * 测试 ByteBufUtil
     */
    @Test
    public void test05() {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello world!", CharsetUtil.UTF_8);
        // 将 ByteBuf 中的内容转换为 16 进制字符串
        String content = ByteBufUtil.hexDump(byteBuf);
        System.out.println(content);
    }

}
