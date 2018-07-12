package com.example.java.base;

class T {
    static {
        String s = "hello world!";
        System.out.println("第二个静态代码块执行...");
    }

    @Deprecated
    public static int FOO = 1;
}

public class RTTITests {

    static {
        System.out.println("静态代码块执行...");
    }

    @SuppressWarnings({"deprecation"})
    public static void main(String[] args) {
        System.out.println("hello world!");
        System.out.println(T.FOO);
    }
}
