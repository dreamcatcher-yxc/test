package com.example.thread;

public class Demo02 {

    public class SynchronizedDemo {
        public void method() {
            synchronized (this) {
                System.out.println("synchronized 代码块");
            }
        }
    }
}
