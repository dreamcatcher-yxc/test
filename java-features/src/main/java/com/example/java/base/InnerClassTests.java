package com.example.java.base;

public class InnerClassTests {

    private static String STATIC_FOO1 = "hello";

    private String foo1 = "world";

    // 静态内部类
    static class InnerStaticClass {
        private String foo1;

        public InnerStaticClass(String foo1) {
            this.foo1 = foo1;
        }

        @Override
        public String toString() {
            return "InnerStaticClass{" +
                    "foo1='" + foo1 + '\'' +
                    '}';
        }


    }

    // 成员内部类
    class InnerClass {
        private String foo1;

        public InnerClass() {
            this.foo1 = InnerClassTests.this.foo1;
        }

        @Override
        public String toString() {
            return "InnerClass{" +
                    "foo1='" + foo1 + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        InnerStaticClass innerStaticClass = new InnerStaticClass("foo1");
        System.out.println(innerStaticClass);

        InnerClass innerClass = new InnerClassTests().new InnerClass();
        System.out.println(innerClass);
    }
}
