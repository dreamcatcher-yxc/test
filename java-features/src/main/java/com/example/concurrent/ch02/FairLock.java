package com.example.concurrent.ch02;

import java.util.ArrayList;
import java.util.List;

public class FairLock {

    private boolean isLocked = false;

    private Thread lockingThread = null;

    private List<QueueObject> waitingThreads = new ArrayList<>();

    /**
     * 上锁, 此方法不能上同步锁, 否则会有死锁发生, 和 &lt;1&gt; 中出现的原因是类似的。
     * @throws InterruptedException
     */
    public void lock() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " 尝试获取锁...");

        QueueObject queueObject = new QueueObject();
        boolean  isLockedForThisThread = true;

        // waitingThreads.add 非线程安全, 需要再同步块中调动
        synchronized(this) {
            waitingThreads.add(queueObject);
        }

        while (isLockedForThisThread) {
            synchronized (this) {
                isLockedForThisThread = this.isLocked || this.waitingThreads.get(0) != queueObject;

                if(!isLockedForThisThread) {
                    System.out.println(Thread.currentThread().getName() + " 获得锁...");
                    this.isLocked = true;
                    this.waitingThreads.remove(queueObject);
                    this.lockingThread = Thread.currentThread();
                    return;
                }
            }

            // <1>;
            // 此部分不能放在同步块中, 若放在同步块中, 会有多个线程同时等待获取该锁, 多线程环境下会出现死锁。
            // 如: A 现在持有此锁, A 会退出方法, A 释放锁。此时 B 尝试获取该锁, 则会等待(等待期间持有锁),
            // 此时 A 延迟一段时间再释放锁, 调用 unlock, unlock 为同步块, 需要获取锁, 则会等待, 因为已经
            // 被 A 持有, 而 B 因为调用了 doWait 方法, 需要依赖 A 的 unlock 方法中调用 doNotify 方法唤醒,
            // 因此造成死锁.
            try {
                System.out.println(Thread.currentThread().getName() + " 获取锁失败, 等待锁释放...");
                queueObject.doWait();
                System.out.println(Thread.currentThread().getName() + " 获得锁, 继续执行...");
            } catch (InterruptedException e) {
                synchronized(this) { waitingThreads.remove(queueObject); }
                throw e;
            }
        }
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

        if(waitingThreads.size() > 0) {
            waitingThreads.get(0).doNotify();
        }
    }
}
