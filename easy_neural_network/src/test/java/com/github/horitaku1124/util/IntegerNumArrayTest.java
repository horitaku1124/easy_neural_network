package com.github.horitaku1124.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class IntegerNumArrayTest {

    @Test
    public void test1() {
        IntegerNumArray target = new IntegerNumArray(new Integer[][] {
                {1, 2, 3},
                {4, 5, 6},
        });

        IntegerNumArray data = new IntegerNumArray(new Integer[][] {
                {1}, {2}, {3},
        });
        IntegerNumArray result = target.dot(data);
        assertThat(result.get(0), is(14));
        assertThat(result.get(1), is(32));

    }
}
