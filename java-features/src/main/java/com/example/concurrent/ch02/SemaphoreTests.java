package com.example.concurrent.ch02;

class SendingThread extends Thread {

    private Semaphore semaphore = null;

    public SendingThread(Semaphore semaphore){
        this.semaphore = semaphore;
    }

    public void run(){
        while(true){
            //do something, then signal
            // 执行之后, 通知另外一个等待此处结算结果的线程
            System.out.println("SendingThread running...");
            this.semaphore.take();
        }
    }

}

class ReceivingThread extends Thread {

    private Semaphore semaphore = null;

    public ReceivingThread(Semaphore semaphore){
        this.semaphore = semaphore;
    }

    public void run() {
        while(true){
            // 执行到此处, 线程挂起等待, 等待信号量
            try {
                //receive signal, then do something...
                // 接收到同步信号量, 继续执行
                this.semaphore.release();
                System.err.println("ReceivingThread running...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class SemaphoreTests {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore();
        Thread sender = new SendingThread(semaphore);
        Thread receiver = new ReceivingThread(semaphore);
        receiver.start();
        sender.start();
    }
}
