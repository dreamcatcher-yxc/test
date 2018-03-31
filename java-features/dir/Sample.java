package com.example;

public class Sample {

    public void sayHello(String name) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(contextClassLoader);
        System.out.println("hello, " + name + "!!!");
    }
}