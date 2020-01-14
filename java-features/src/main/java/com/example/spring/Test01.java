package com.example.spring;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test01 {

    public static class SimpleBean {

        private String foo1;

        private String foo2;

        public String getFoo1() {
            return foo1;
        }

        public void setFoo1(String foo1) {
            this.foo1 = foo1;
        }

        public String getFoo2() {
            return foo2;
        }

        public void setFoo2(String foo2) {
            this.foo2 = foo2;
        }

        @Override
        public String toString() {
            return "SimpleBean{" +
                    "foo1='" + foo1 + '\'' +
                    ", foo2='" + foo2 + '\'' +
                    '}';
        }
    }

    @Test
    public void test01() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        SimpleBean bean = context.getBean(SimpleBean.class);
        System.out.println(bean);
        context.close();
    }
}
