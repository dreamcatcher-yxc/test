package com.example.netty.demo07;

import io.netty.channel.Channel;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 线程模型
 */
public class demo01 {

    /**
     * jdk 1.5 之后提供的定时任务执行方法
     */
    @Test
    public void test01() {
        ScheduledExecutorService executor =
                Executors.newScheduledThreadPool(10);
        ScheduledFuture<?> future = executor.schedule(() -> System.out.println("10 seconds later"), 10, TimeUnit.SECONDS);
        // 没完成就等
        while (!future.isDone());
        // 调度完成, 就关闭 ScheduledExecutorService, 释放资源
        executor.shutdown();
    }

    /**
     * 才 Netty 的方式实现, 其实也是对 jdk 线程模型的封装
     */
    @Test
    public void test02() {
        Channel channel = new EmbeddedChannel();
        io.netty.util.concurrent.ScheduledFuture<?> future = channel.eventLoop().schedule(() -> System.out.println("10 seconds later"), 10, TimeUnit.SECONDS);
        // 没完成就等
        while (!future.isDone());
        // 调度完成, 就关闭 ScheduledExecutorService, 释放资源
//        future.syncUninterruptibly();
        // 取消该任务, 防止该任务继续执行
        future.cancel(true);
    }
}
