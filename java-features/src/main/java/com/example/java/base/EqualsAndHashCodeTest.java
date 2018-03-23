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
}
