package com.example.java.classloader;

import org.junit.Test;

import java.lang.reflect.Method;

public class Demo01 {

    @Test
    public void test01() {
        ClassLoader classLoader = Demo01.class.getClassLoader();
        ClassLoader parentClassLoader = classLoader.getParent();
        System.out.println(classLoader.toString());
        System.out.println(parentClassLoader.toString());
    }

    @Test
    public void test02() throws Exception {
        MyClassLoader loader1 = new MyClassLoader("./dir");
        Class<?> clazz = loader1.findClass("com.example.Sample");
        Object sample = clazz.newInstance();
        Method method = sample.getClass().getDeclaredMethod("sayHello", String.class);
        method.invoke(sample, "yangxiuchu");
        System.out.println(clazz.getClassLoader());
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(contextClassLoader);
    }

}
