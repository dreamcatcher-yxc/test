package com.example.java.base;

import java.util.HashMap;
import java.util.Map;

class Person {
    public static int EQUALS_METHOD_INVOKE_COUNT = 0;
    private String name;
    private int age;
    private String sex;

    public Person() {}

    public Person(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public boolean equals(Object obj) {
        EQUALS_METHOD_INVOKE_COUNT++;
        System.out.println("Invoke " + this.toString() + " equals method...(" + EQUALS_METHOD_INVOKE_COUNT + ")");

        if(!(obj instanceof Person)) {
            return false;
        }

        Person tp = (Person) obj;

        if(this.name.equals(tp.getName())
                && this.age == tp.getAge()
                && this.sex.equals(tp.getSex())) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }
}

public class EqualsTests {
    public static void main(String[] args) {
        Map<Person, Person> map = new HashMap<>();

        for(int i = 0; i < 6; i++) {
            String name = "name-" + i;
            int age = (int)(Math.random() * (50 - 20 + 1)) + 20;
            String sex = (int)(Math.random() * 10) > 2 ? "M" : "F";
            Person pk = new Person("key-" + name, age, sex);
            Person pv = new Person("value-" + name, age, sex);
            map.put(pk, pv);
        }
    }
}
