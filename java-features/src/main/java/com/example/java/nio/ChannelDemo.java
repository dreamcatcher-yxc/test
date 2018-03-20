package com.example.java.nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelDemo {

    /**
     * 测试 buffer
     * @throws Exception
     */
    @Test
    public void testBuffer01() throws Exception {
        RandomAccessFile aFile = new RandomAccessFile("D:/hadoop-tmp/word.txt", "rw");
        // 获取通道
        FileChannel channel = aFile.getChannel();
        // 分配一个大小的 64 字节的 byte 类型的缓冲区
        ByteBuffer buff = ByteBuffer.allocate(64);
        // 向缓冲区里面写入数据
        int bytesRead = channel.read(buff);
        while (bytesRead != -1) {
            /**
             * 将 buffer 从写模式转换为读模式, 该操作会导致 buffer 的 limit 置为 position, position 置为 0.
             */
            buff.flip();
            while (buff.hasRemaining()) {
                System.out.println((char)buff.get());
            }
            /**
             * 将 buffer 从读模式转换为写模式, 该操作会导致 buffer 的 limit 置为 capacity(这里为 64), position 置为 0,
             * 相似的方法还有 compact, 该方法会把 limit 置为 capacity(这里为 64), position 设置为当前读到的数据的下一个
             * 位置, 如 position = 3, limit = 5, capacity = 64, 表示已经读取了 3 个数字, 但是还差两个字节的数据没有读取,
             * 这里就会将会: position = 3, limit = capacity = 64, 头两位为还没有上次还没有读取完成的数据.
             */
            buff.clear();
            bytesRead = channel.read(buff);
        }
        aFile.close();
    }

    /**
     * 测试 buffer
     * @throws Exception
     */
    @Test
    public void testBuffer02() throws Exception{
        FileInputStream in = new FileInputStream("D:/hadoop-tmp/word.txt");
        FileChannel channel = in.getChannel();
        ByteBuffer buff = ByteBuffer.allocate(64);
        while (channel.read(buff) > 0) {
            buff.flip();
            byte[] tbs = new byte[buff.limit()];
            buff.get(tbs);
            System.out.println(new String(tbs));
            buff.clear();
        }
        in.close();
    }

    /**
     * 测试 buffer 的 Compact 方法
     * @throws Exception
     */
    @Test
    public void testCompact() throws Exception {
        ByteBuffer buff = ByteBuffer.allocate(10);
        buff.put((byte)'h');
        buff.put((byte)'e');
        buff.put((byte)'l');
        buff.put((byte)'l');
        buff.put((byte)'o');

        buff.flip();
        System.out.println(String.format("position is %d, data is %s", buff.position(), (char)buff.get()));
        System.out.println(String.format("position is %d, data is %s", buff.position(), (char)buff.get()));
        System.out.println(String.format("position is %d, data is %s", buff.position(), (char)buff.get()));

        buff.compact();
        System.out.println(String.format("position is %d", buff.position()));
        System.out.println((char)buff.get(0));
        System.out.println((char)buff.get(1));
        System.out.println("limit is " + buff.limit());
    }

    /**
     * 使用 ScatteringReads 将 channel 中的数据放入多个 buffer 中, channel 中的数据会按照 buffer 数组中的 buffer 顺序
     * 放入。
     * @throws Exception
     */
    @Test
    public void testScatteringReads() throws Exception {
        FileInputStream in = new FileInputStream("D:/hadoop-tmp/word.txt");
        FileChannel channel = in.getChannel();
        ByteBuffer buff01 = ByteBuffer.allocate(112);
        ByteBuffer buff02 = ByteBuffer.allocate(64);
        ByteBuffer[] bufferArray = {buff01, buff02};
        channel.read(bufferArray);
        buff01.flip();
        buff02.flip();
        System.out.println("buff01 limit is: " + buff01.limit());
        System.out.println("buff02 limit is: " + buff02.limit());
    }

    /**
     * 使用 ScatteringWrites 将多个 buffer 中的数据放入 channel, 每个 buffer 中有效的数据会被放入放入 channel 中。
     * @throws Exception
     */
    @Test
    public void testScatteringWrites() throws Exception {
        FileOutputStream out = new FileOutputStream("D:/hadoop-tmp/word.txt");
        FileChannel channel = out.getChannel();
        ByteBuffer buff01 = ByteBuffer.allocate(112);
        buff01.put((byte)'h');
        buff01.put((byte)'0');
        buff01.put((byte)'1');
        ByteBuffer buff02 = ByteBuffer.allocate(64);
        buff02.put((byte)'h');
        buff02.put((byte)'0');
        buff02.put((byte)'2');
        ByteBuffer[] bufferArray = {buff01, buff02};
        channel.write(bufferArray);
        channel.close();
    }

    /**
     * 使用 transferFrom 实现文件之间数据的复制
     * @throws Exception
     */
    @Test
    public void testTransferFrom() throws Exception {
        RandomAccessFile fromFile = new RandomAccessFile("D:/hadoop-tmp/from.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();
        RandomAccessFile toFile = new RandomAccessFile("D:/hadoop-tmp/to.txt", "rw");
        FileChannel toChannel = toFile.getChannel();
        toChannel.transferFrom(fromChannel, 0L, fromChannel.size());
    }

    /**
     * 使用 transferTo 实现文件之间数据的复制
     * @throws Exception
     */
    @Test
    public void testTransferTo() throws Exception {
        RandomAccessFile fromFile = new RandomAccessFile("D:/hadoop-tmp/from.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();
        RandomAccessFile toFile = new RandomAccessFile("D:/hadoop-tmp/to.txt", "rw");
        FileChannel toChannel = toFile.getChannel();
        fromChannel.transferTo(0L, fromChannel.size(), toChannel);
    }

}
