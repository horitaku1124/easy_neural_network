package com.github.horitaku1124.util;

import org.junit.Test;

import static java.lang.Math.abs;
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertTrue;

public class MyNumArrayTest {

    private boolean isCloseTo(float actual, float expected) {
        final float error = 0.001f;
        return abs(actual - expected) < error;
    }

    @Test
    public void test1() {
        MyNumArray num = new MyNumArray(2, 4);
        num.set(1.0f, 0, 0);
        num.set(2.0f, 0, 1);
        num.set(3.0f, 0, 2);
        num.set(4.0f, 0, 3);
        num.set(5.0f, 1, 0);
        num.set(6.0f, 1, 1);
        num.set(7.0f, 1, 2);
        num.set(8.0f, 1, 3);

        assertTrue(isCloseTo(num.get(0, 0), 1f));
        assertTrue(isCloseTo(num.get(0, 3), 4f));
        assertTrue(isCloseTo(num.get(1, 2), 7f));
        assertTrue(isCloseTo(num.get(1, 3), 8f));
    }
    @Test
    public void test2() {
        MyNumArray num = new MyNumArray(2, 3, 4);
        num.set(1.0f, 0, 0, 0);
        num.set(2.0f, 0, 0, 1);
        num.set(3.0f, 0, 0, 2);
        num.set(4.0f, 0, 0, 3);
        num.set(5.0f, 0, 1, 0);
        num.set(8.0f, 0, 1, 3);
        num.set(9.0f, 0, 2, 0);
        num.set(11.0f, 0, 2, 3);
        num.set(12.0f, 1, 0, 0);
        num.set(18.0f, 1, 1, 2);
        num.set(23.0f, 1, 2, 3);

        assertTrue(isCloseTo(num.get(0, 0, 0), 1f));
        assertTrue(isCloseTo(num.get(0, 2, 0), 9f));
        assertTrue(isCloseTo(num.get(1, 2, 3), 23f));
    }
    @Test
    public void test3() {
        MyNumArray num = new MyNumArray(2, 3, 4, 5);
        num.set(1.0f, 0, 0, 0, 0);
        num.set(2.0f, 0, 0, 1, 0);
        num.set(3.0f, 0, 1, 0, 0);
        num.set(4.0f, 1, 0, 0, 0);
        num.set(120.0f, 1, 2, 3, 4);
        assertTrue(isCloseTo(num.get(0, 0, 0, 0), 1f));
        assertTrue(isCloseTo(num.get(1, 2, 3, 4), 120f));
    }
}
