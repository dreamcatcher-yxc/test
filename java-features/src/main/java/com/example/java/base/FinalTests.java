package com.example.java.base;

class A {
    public static void a() {};
}

public class FinalTests extends A {

    @Override
    protected void finalize() throws Throwable {
        System.out.println("执行 finalize...");
    }

    public static void main(String[] args) {
        FinalTests finalTests = new FinalTests();
        finalTests = null;
        System.gc();
    }
}
