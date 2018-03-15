package com.example.demo.entity.p1;

import java.io.Serializable;

public class Foo01 implements Serializable {
    private static final long serialVersionUID = 1L;

    private String foo01Prop01;

    private String foo02Prop02;

    public String getFoo01Prop01() {
        return foo01Prop01;
    }

    public void setFoo01Prop01(String foo01Prop01) {
        this.foo01Prop01 = foo01Prop01;
    }

    public String getFoo02Prop02() {
        return foo02Prop02;
    }

    public void setFoo02Prop02(String foo02Prop02) {
        this.foo02Prop02 = foo02Prop02;
    }

    @Override
    public String toString() {
        return "Foo01{" +
                "foo01Prop01='" + foo01Prop01 + '\'' +
                ", foo02Prop02='" + foo02Prop02 + '\'' +
                '}';
    }
}
