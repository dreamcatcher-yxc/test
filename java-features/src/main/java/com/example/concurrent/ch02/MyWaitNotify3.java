package com.example.concurrent.ch02;

public class MyWaitNotify3 {
    // 使用字符串常量或者或者全局对象
    private final String myMonitorObject = "";

    // 标识是否已经被同时
    private volatile boolean wasSignalled = false;

    public void doWait() {
        synchronized (myMonitorObject) {
            // 如果还没有被通知, 则等待
            while (!wasSignalled) {
                try {
                    System.out.println("before " + Thread.currentThread().getName() + " wait...");
                    myMonitorObject.wait();
                    System.out.println("after " + Thread.currentThread().getName() + " wait...");
                } catch (InterruptedException e) {}
                wasSignalled = false;
            }
        }
    }

    public void doNotify() {
        synchronized (myMonitorObject) {
            // 通知了则标识状态为已通知
            wasSignalled = true;
            System.out.println("before " + Thread.currentThread().getName() + " notify...");
            myMonitorObject.notify();
            System.out.println("after " + Thread.currentThread().getName() + " notify...");
        }
    }

    public static void main(String[] args) {
        MyWaitNotify3 handler1 = new MyWaitNotify3();
        MyWaitNotify3 handler2 = new MyWaitNotify3();

        new Thread(() ->{
            Thread.currentThread().setName("A");
            handler1.doWait();
        }).start();

        new Thread(() -> {
            Thread.currentThread().setName("B");
            handler1.doWait();
        }).start();

        new Thread(() ->{
            Thread.currentThread().setName("C");
            handler2.doWait();
        }).start();

        new Thread(() ->{
            Thread.currentThread().setName("D");
            handler2.doWait();
        }).start();

        new Thread(() -> {
            Thread.currentThread().setName("E");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler2.doNotify();
        }).start();
    }
}
