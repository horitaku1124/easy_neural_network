package com.github.horitaku1124.util;

import org.junit.Test;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MyNumArrayTest {
    @Test
    public void verifySetAndGetWorks2d () {
        MyNumArray num = new MyNumArray(2, 4);
        num.set(1.0f, 0, 0);
        num.set(2.0f, 0, 1);
        num.set(3.0f, 0, 2);
        num.set(4.0f, 0, 3);
        num.set(5.0f, 1, 0);
        num.set(6.0f, 1, 1);
        num.set(7.0f, 1, 2);
        num.set(8.0f, 1, 3);

        assertThat((double)num.get(0, 0), is(closeTo(1.0f, 0.001f)));
        assertThat((double)num.get(0, 3), is(closeTo(4.0f, 0.001f)));
        assertThat((double)num.get(1, 2), is(closeTo(7.0f, 0.001f)));
        assertThat((double)num.get(1, 3), is(closeTo(8.0f, 0.001f)));
    }
    @Test
    public void verifySetAndGetWorks3d() {
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

        assertThat((double)num.get(0, 0, 0), is(closeTo(1.0f, 0.001f)));
        assertThat((double)num.get(0, 2, 0), is(closeTo(9.0f, 0.001f)));
        assertThat((double)num.get(1, 2, 3), is(closeTo(23.0f, 0.001f)));
    }
    @Test
    public void verifySetAndGetWorks4d() {
        MyNumArray num = new MyNumArray(2, 3, 4, 5);
        num.set(1.0f, 0, 0, 0, 0);
        num.set(2.0f, 0, 0, 1, 0);
        num.set(3.0f, 0, 1, 0, 0);
        num.set(4.0f, 1, 0, 0, 0);
        num.set(120.0f, 1, 2, 3, 4);
        assertThat((double)num.get(0, 0, 0, 0), is(closeTo(1.0f, 0.001f)));
        assertThat((double)num.get(1, 2, 3, 4), is(closeTo(120.0f, 0.001f)));
    }
}
