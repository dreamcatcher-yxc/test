package com.example.concurrent.ch02;

/**
 * 可重入锁
 */
public class ReentrantLock {

    private boolean isLocked  = false;

    private Thread lockedBy = null;

    private int lockedCount = 0;

    public synchronized void lock() throws InterruptedException {
        Thread callingThread = Thread.currentThread();

        while (isLocked && callingThread != lockedBy) {
            wait();
        }

        isLocked = true;
        lockedBy = callingThread;
        lockedCount++;
    }

    public synchronized void unlock() {
        if(Thread.currentThread() == lockedBy) {
            lockedCount--;

            if(lockedCount == 0) {
                isLocked = false;
                lockedBy = null;
                notify();
            }
        }
    }
}
