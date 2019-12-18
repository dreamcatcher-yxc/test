package com.example.concurrent.ch02;

import java.util.concurrent.CountDownLatch;

public class ThreadPoolTests {

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(10);
        ThreadPool threadPool = new ThreadPool(5, 10);

        for(int i = 0; i < 10; i++) {
            final int t = i;
            threadPool.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(t);
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        threadPool.stop();
    }
}
