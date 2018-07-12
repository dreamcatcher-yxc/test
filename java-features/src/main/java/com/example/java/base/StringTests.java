package com.example.java.base;

import org.junit.Test;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class StringTests {

    private BiConsumer<Consumer, String> timer = (consumer, title) -> {
        long start = System.currentTimeMillis();
        consumer.accept(null);
        long end = System.currentTimeMillis();
        System.out.println(String.format("[%s]耗时:%s", title, end - start));
    };

    private final int count = 50000;

    @Test
    public void test01() {
        // Java 的字符串也有常量池的概念, str1 以常量的方式初始化, str3 也是以常量的方式初始化, 两个都是一样的字符串,
        // 所有 str3 和 str1 所指向的对象都是一个, 地址自然也相同, str2、str4 都是以 new 的方式初始化的, 不是同一个
        // 对象, 地址自然不同。
        String str1 = "hello world!";
        String str2 = new String("hello world!");
        String str3 = "hello world!";
        String str4 = new String("hello world!");

        System.out.println(str1 == str2); // false
        System.out.println(str1 == str3); // true
        System.out.println(str2 == str4); // false
    }

    @Test
    public void test02() {
        timer.accept( foo -> {
            String str = "";
            for(int i = 0; i < count; i++) {
                str += "java";
            }
        }, String.class.getName());

        timer.accept(foo -> {
            StringBuffer sb = new StringBuffer();
            for(int i = 0; i < count; i++) {
                sb.append("java");
            }
        }, StringBuffer.class.getName());

        timer.accept(foo -> {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < count; i++) {
                sb.append("java");
            }
        }, StringBuilder.class.getName());
    }

    @Test
    public void test03() {
        String str1 = "hello1";
        // 编译的时候会被优化为 "hello1"
        String str2 = "hello" + 1;
        System.out.println(str1 == str2); // true
    }

    @Test
    public void test04() {
        String a = "hello1";
        String b = "hello";
        // 存在符号变量, 在编译的时候无法使用常量池优化。
        String c = b + 1;
        System.out.println(a == c);
    }

    @Test
    public void test05() {
        String a = "hello1";
        final String b = "hello";
        // 由于 b 是 final 类型的，在编译的时候会被优化为 "hello1"
        String c = b + 1;
        System.out.println(a == c);
    }

    private String getHello() {
        return "hello";
    }

    @Test
    public void test06() {
        String a = "hello1";
        // 虽然 b 是 final 类型的, 但是其值是通过方法的返回结果获取的, 运行的时候动态确定, 编译的时候无法优化。
        final String b = getHello();
        String c = b + 1;
        System.out.println(a == c); // false
    }
}
