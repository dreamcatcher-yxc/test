package com.example.concurrent.ch02;

/**
 * 锁测试
 */
public class LockTests {

    public static void simpleLockTest() throws Exception {
        Lock lock = new Lock(); // 创建一把锁

        for(int i = 0; i < 10; i++) {
            final String threadName = "thread-" + (char)(65 + i);

            new Thread(() -> {
                try {
                    Thread.currentThread().setName(threadName);
                    lock.lock(); // 上锁
                    System.out.println(Thread.currentThread().getName() + " lock...");
                    System.out.println("before " + Thread.currentThread().getName() + " sleeping 1 seconds...");
                    Thread.sleep(1000); // 休眠 1 S
                    System.out.println("after " + Thread.currentThread().getName() + " sleeping 1 seconds...");
                    System.out.println("after " + Thread.currentThread().getName() + " lock...");
                    System.out.println(Thread.currentThread().getName() + " unlock...\r\n\r\n");
                    lock.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public static void fairLockTest() throws Exception {
        FairLock lock = new FairLock(); // 创建一把锁

        for(int i = 0; i < 10; i++) {
            final String threadName = "thread-" + (char)(65 + i);

            new Thread(() -> {
                try {
                    Thread.currentThread().setName(threadName);
                    lock.lock(); // 上锁
                    System.out.println(Thread.currentThread().getName() + " lock...");
                    System.out.println("before " + Thread.currentThread().getName() + " sleeping 1 seconds...");
                    Thread.sleep(1000); // 休眠 1 S
                    System.out.println("after " + Thread.currentThread().getName() + " sleeping 1 seconds...");
                    System.out.println("after " + Thread.currentThread().getName() + " lock...");
                    System.out.println(Thread.currentThread().getName() + " unlock...\r\n\r\n");
                    lock.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public static void main(String[] args) throws Exception {
//        simpleLockTest();
        fairLockTest();
    }
}
