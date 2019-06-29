package com.example.concurrent.ch02;

public class MyWaitNotify2 {

    private final MonitorObject myMonitorObject = new MonitorObject();

    // 标识是否已经被同时
    private volatile boolean wasSignalled = false;

    public void doWait() {
        synchronized (myMonitorObject) {
            // 如果还没有被通知, 则等待
            if(!wasSignalled) {
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
        MyWaitNotify2 demo01 = new MyWaitNotify2();

        // 第一个线程调用 doWait 方法, 标识当前运行此线程的方法将会被挂起, 等待有其他线程调用 notify 方法唤醒此线程,
        // wait 方法同时也会释放 synchronized 对象的锁。
        new Thread(() ->{
            demo01.doWait();
        }).start();

        // 第二个线程间隔三秒调用 doNotify 方法, 唤醒第一个线程的 wait 方法继续执行.
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            demo01.doNotify();
        }).start();
    }

}
