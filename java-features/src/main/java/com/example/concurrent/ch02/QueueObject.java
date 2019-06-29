package com.example.concurrent.ch02;

public class QueueObject {
    private boolean isNotified = false;

    /**
     * 上锁
     *
     * @throws InterruptedException
     */
    public synchronized void doWait() throws InterruptedException {
        while (!this.isNotified) {
            this.wait();
        }
        this.isNotified = false;
    }

    /**
     * 释放锁
     */
    public synchronized void doNotify() {
        this.isNotified = true;
        this.notify();
        System.out.println(Thread.currentThread().getName() + " 唤醒栈顶等待线程...");
    }
}