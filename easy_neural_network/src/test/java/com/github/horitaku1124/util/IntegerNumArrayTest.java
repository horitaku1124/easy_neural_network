package com.github.horitaku1124.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class IntegerNumArrayTest {

    @Test
    public void verify3x2_dot_3x1_results_2() {
        IntegerNumArray target = new IntegerNumArray(new Integer[][] {
                {1, 2, 3},
                {4, 5, 6},
        });

        IntegerNumArray data = new IntegerNumArray(new Integer[][] {
                {1}, {2}, {3},
        });
        IntegerNumArray result = target.dot(data);
        assertThat(result.ndim, is(1));
        assertThat(result.shape, is(new int[]{2}));
        assertThat(result.get(0), is(14));
        assertThat(result.get(1), is(32));

    }

    @Test
    public void verify1d_dot_1d_results_single() {
        IntegerNumArray target = new IntegerNumArray(new Integer[] {
                1, 2,
        });
        IntegerNumArray data = new IntegerNumArray(new Integer[] {
                4, 3,
        });
        IntegerNumArray result = target.dot(data);
        assertThat(result.ndim, is(1));
        assertThat(result.shape, is(new int[]{1}));
        assertThat(result.get(0), is(10));


        target = new IntegerNumArray(new Integer[] {
                1, 2, 3
        });
        data = new IntegerNumArray(new Integer[] {
                2, 4, 6,
        });
        result = target.dot(data);
        assertThat(result.ndim, is(1));
        assertThat(result.shape, is(new int[]{1}));
        assertThat(result.get(0), is(28));
    }

    @Test
    public void verify_2d_dot_2d_results_2d() {
        IntegerNumArray target;
        IntegerNumArray result;
        IntegerNumArray data;

        target = new IntegerNumArray(new Integer[][] {
                {1, 1},
                {2, 2},
                {3, 3},
        });

        data = new IntegerNumArray(new Integer[][] {
                {1, 2},
                {3, 4},
        });
        result = target.dot(data);
        assertThat(result.ndim, is(2));
        assertThat(result.shape, is(new int[]{3, 2}));
        assertThat(result.get(0, 0), is(4));
        assertThat(result.get(0, 1), is(6));
        assertThat(result.get(1, 0), is(8));
        assertThat(result.get(1, 1), is(12));
        assertThat(result.get(2, 0), is(12));
        assertThat(result.get(2, 1), is(18));


        target = new IntegerNumArray(new Integer[][] {
                {1, 1},
                {2, 2},
                {3, 3},
                {4, 4},
        });
        data = new IntegerNumArray(new Integer[][] {
                {1, 2, 3},
                {4, 5, 6},
        });
        result = target.dot(data);
        assertThat(result.ndim, is(2));
        assertThat(result.shape, is(new int[]{4, 3}));
        assertThat(result.get(0, 0), is(5));
        assertThat(result.get(0, 1), is(7));
        assertThat(result.get(0, 2), is(9));
        assertThat(result.get(1, 0), is(10));
        assertThat(result.get(1, 1), is(14));
        assertThat(result.get(1, 2), is(18));
        assertThat(result.get(2, 0), is(15));
        assertThat(result.get(2, 1), is(21));
        assertThat(result.get(2, 2), is(27));
        assertThat(result.get(3, 0), is(20));
        assertThat(result.get(3, 1), is(28));
        assertThat(result.get(3, 2), is(36));
    }

    @Test
    public void verifyTransposeWorks() {
        IntegerNumArray target;

        target = new IntegerNumArray(new Integer[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12},
        });
        IntegerNumArray result = target.transpose();
        assertThat(result.ndim, is(2));
        assertThat(result.get(0, 0), is(1));
        assertThat(result.get(0, 1), is(4));
        assertThat(result.get(0, 2), is(7));
        assertThat(result.get(0, 3), is(10));
        assertThat(result.get(1, 0), is(2));
        assertThat(result.get(1, 1), is(5));
        assertThat(result.get(1, 2), is(8));
        assertThat(result.get(1, 3), is(11));
        assertThat(result.get(2, 0), is(3));
        assertThat(result.get(2, 1), is(6));
        assertThat(result.get(2, 2), is(9));
        assertThat(result.get(2, 3), is(12));
        assertThat(result.shape, is(new int[]{3, 4}));
    }

}
