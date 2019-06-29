package com.example.concurrent.ch02;

import java.util.Random;

public class ThreadLocalTests {

    public static class MyRunnable implements Runnable {

        private ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            int randomValue = Math.abs(new Random().nextInt()) % 10;
            threadLocal.set(randomValue);
            System.out.println(String.format("%s set: %s", threadName, randomValue));

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(threadLocal.get());
            System.out.println(String.format("%s get: %s", threadName, randomValue));
        }
    }

    public static void main(String[] args) {
        new Thread(new MyRunnable()).start();
        new Thread(new MyRunnable()).start();
    }
}
