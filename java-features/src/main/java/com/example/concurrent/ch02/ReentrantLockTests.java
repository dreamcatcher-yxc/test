package com.example.concurrent.ch02;

public class ReentrantLockTests {

    public static void main(String[] args) {
        final ReentrantLock reentrantLock = new ReentrantLock();

        new Thread(() -> {
            try {
                reentrantLock.lock();
                System.out.println("lock-1");
                reentrantLock.lock();
                System.out.println("lock-2");
                reentrantLock.unlock();
                System.out.println("unlock-1");
                reentrantLock.unlock();
                System.out.println("unlock-2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
