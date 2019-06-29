package com.example.concurrent.ch02;

public class Lock {

    private boolean isLocked = false;

    private Thread lockingThread = null;

    /**
     * 上锁
     * @throws InterruptedException
     */
    public synchronized void lock() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " 尝试获取锁...");

        // 只要 Lock 已经上锁, 当前线程就挂起等待
        while (this.isLocked) {
            System.out.println(Thread.currentThread().getName() + " 获取锁失败, 等待锁释放...");
            wait();
        }

        this.isLocked = true;
        this.lockingThread = Thread.currentThread();
        System.out.println(this.lockingThread.getName() + " 获得锁...");
    }

    /**
     * 释放锁
     */
    public synchronized void unlock() {
        if(Thread.currentThread() != this.lockingThread) {
            throw new IllegalMonitorStateException("Calling thread has not locked this lock");
        }

        this.isLocked = false;
        this.lockingThread = null;
        notify(); // 唤醒在等待本实例释放的锁
    }
}
