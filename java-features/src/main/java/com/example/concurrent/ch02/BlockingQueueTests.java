package com.example.concurrent.ch02;

import java.util.concurrent.CountDownLatch;

public class BlockingQueueTests {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(2);

        BlockingQueue blockingQueue = new BlockingQueue(10);

        new Thread(() -> {
            int i = 0;
            while (i++ < 10) {
                try {
                    double random = Math.random();
                    blockingQueue.enqueue(random);
                    System.out.println("put " + random);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }
        }).start();

        new Thread(() -> {
            int i = 0;
            while (i++ < 10) {
                try {
                    Double dequeue = (Double) blockingQueue.dequeue();
                    System.out.println("get " + dequeue);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            countDownLatch.countDown();
        }).start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
