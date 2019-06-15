package com.example.netty.demo03;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelPipeline;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.logging.Logger;

public class Demo01 {

    private static final Logger log = Logger.getLogger(Demo01.class.getName());

    @Test
    public void test1() {
        ByteBuf heapBuf = Unpooled.unreleasableBuffer(
                Unpooled.copiedBuffer("Hi!\r\n", Charset.forName("UTF-8")));

        if (heapBuf.hasArray()) {                //1
            byte[] array = heapBuf.array();        //2
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();                //3
            int length = heapBuf.readableBytes();   //4

            log.info(Arrays.toString(array));
            log.info(String.valueOf(offset));
            log.info(String.valueOf(length));
        }
    }

    @Test
    public void test2() {
        ByteBuf directBuffer = Unpooled.unreleasableBuffer(
                Unpooled.directBuffer(10));
        directBuffer.setIndex(0, 0);
        directBuffer.setBytes(0, "HI!\r\n".getBytes());
        directBuffer.setIndex(0, 4);

        log.info(directBuffer.capacity() + "");

        for (int i = 0; i < directBuffer.capacity(); i++) {
            byte b = directBuffer.getByte(i);
            System.out.println((char) b);
        }
    }

    @Test
    public void test03() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8); //1

        // 创建一个副本, 但是引用的还是原数据, 相当于指针数组.
        ByteBuf sliced = buf.slice(0, 14);          //2
        log.info(sliced.toString(utf8));  //3

        buf.setByte(0, (byte) 'J');                 //4
        assert buf.getByte(0) == sliced.getByte(0);
    }

    @Test
    public void test04() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);     //1

        // 直接进行值拷贝
        ByteBuf copy = buf.copy(0, 14);               //2
       log.info(copy.toString(utf8));      //3

        buf.setByte(0, (byte) 'J');                   //4
        assert buf.getByte(0) != copy.getByte(0);
    }

    @Test
    public void test05() {
        ByteBufAllocator allocator = new UnpooledByteBufAllocator(false);
        ByteBuf buffer = allocator.buffer();
        // 当 buffer 的引用为 0, 表示该 buffer 可以被释放了
        log.info(buffer.refCnt() + "");
    }

    @Test
    public void test06() {
//        ChannelPipeline cp = new DefaultChannelPipeline(null);
    }
}
