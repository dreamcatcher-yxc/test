package com.example.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Demo01 {
    public static void main(String[] args) throws IOException {
        // 打开文件
        RandomAccessFile aFile = new RandomAccessFile("data/nio-data.txt", "rw");
        // 打开通道
        FileChannel inChannel = aFile.getChannel();
        // 分配大小为 48 字节的缓存空间
        ByteBuffer buf = ByteBuffer.allocate(48);
        // 读取到缓存
        int count = inChannel.read(buf);

        while (count != -1) {
            System.out.println("count: " + count);
            // 开始读取
            buf.flip();

            while (buf.hasRemaining()) {
                System.out.println((char)buf.get());
            }

            // 清空缓存
            buf.clear();
            // 继续读取
            count = inChannel.read(buf);
        }

        aFile.close();
    }
}
