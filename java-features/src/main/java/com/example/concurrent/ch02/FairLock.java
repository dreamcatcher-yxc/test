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
            // 此部分不能放在同步块中, 既不能 synchronized (this) { }  若放在同步块中, 会有多个线程同时等待获取该锁,
            // 多线程环境下会出现死锁，这种死锁属于嵌套导致的死锁。
            // eg: 当前有两个线程(A、B)使用此锁，名称为 lock, 加入此时 A 线程先获得锁，则有 A{ lock.lock() }, 此时
            // A{ Thread.sleep(1000); // 休眠一秒 }, B 执行 B{ lock.lock(); } 此时 B 会被挂起! 注意, 此时 B 线程
            // 执行下面的代码, 会造成 lock 被锁住(注意: 属于 B 线程的 queueObject 不会被锁住!), 1S 之后, A 线程
            // 苏醒, 执行 A{ lock.unlock(); }, 此时需要 A 需要被 B 线程持有的 lock 释放锁, 而 B 线程释放锁的条件是
            // A 线程释放 lock, 从而导致了死锁。
//            synchronized (this) {
                try {
                    System.out.println(Thread.currentThread().getName() + " 获取锁失败, 等待锁释放...");
                    queueObject.doWait();
                    System.out.println(Thread.currentThread().getName() + " 获得锁, 继续执行...");
                } catch (InterruptedException e) {
                    synchronized(this) { waitingThreads.remove(queueObject); }
                    throw e;
                }
//            }
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
