package com.example.java.base;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class EqualsAndHashCodeTest {

    @Test
    public void test01() {
//       System.out.println(Integer.MAX_VALUE + 1 == Integer.MIN_VALUE);
        Set set = new HashSet();
        set.add('a');
    }

    @Test
    public void test02() {
        Integer i1 = new Integer(1000);
        int i2 = 1000;
        // System.out.println(i1.intValue() == i2);
        System.out.println(i1 == i2); // true
    }

    @Test
    public void test03() {
        Integer i = new Integer(100);
        // Integer j = Integer.valueOf(100);
        Integer j = 100;
        System.out.print(i == j); //false
    }

    @Test
    public void test04() {
        // 默认情况下 -128 ~ 127 之间的整数都会在 Java 虚拟机初始化的时候就初始化了其相应的包装类对象，
        // 其目的在于提高性能。
        Integer i1 = 127; // Integer i1 = Integer.valueOf(127);
        Integer i2 = 127; // Integer i2 = Integer.valueOf(127);
        System.out.println(i1 == i2); // true

        Integer i3 = 128; // Integer i3 - Integer.valueOf(128);
        Integer i4 = 128; // Integer i4 = Integer.valueOf(128);
        System.out.println(i3 == i4); // false
    }
}
