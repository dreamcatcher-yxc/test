package com.example.java.base;

public class Outer {

    public void test01(final String s1) {
        class Inner {
            public void print() {
                System.out.println(s1);
            }
        }
        new Inner().print();
    }

    public void test02(final String s2) {
        new Outer() {
            public void print() {
                System.out.println(s2);
            }
        }.print();
    }

    public static void main(String[] args) {
//        Outer outer = new Outer();
//        outer.test01("test01");
//        outer.test02("test02");
        int i = Integer.parseInt("123");
        System.out.println(i);
    }
}
