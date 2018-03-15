package com.example.demo.entity.p2;

import java.io.Serializable;

public class Foo02 implements Serializable {
    private static final long serialVersionUID = 1L;

    private String foo02Prop01;

    private String foo02Prop02;

    public String getFoo02Prop01() {
        return foo02Prop01;
    }

    public void setFoo02Prop01(String foo02Prop01) {
        this.foo02Prop01 = foo02Prop01;
    }

    public String getFoo02Prop02() {
        return foo02Prop02;
    }

    public void setFoo02Prop02(String foo02Prop02) {
        this.foo02Prop02 = foo02Prop02;
    }

    @Override
    public String toString() {
        return "Foo02{" +
                "foo02Prop01='" + foo02Prop01 + '\'' +
                ", foo02Prop02='" + foo02Prop02 + '\'' +
                '}';
    }
}
