package com.github.horitaku1124.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ArrayWarpTest {
  private final int[] initData = new int[]{1,2,3,4,5};
  @Test
  public void test1() {
    ArrayWarp aw = new ArrayWarp(initData, 1, 3);
    assertThat(aw.size(), is(2));

    assertThat(aw.get(0), is(2));
    assertThat(aw.get(1), is(3));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void test2() {
    ArrayWarp aw = new ArrayWarp(initData, 1, 3);
    assertThat(aw.size(), is(2));

    assertThat(aw.get(0), is(2));
    aw.get(2);
  }

  @Test
  public void test3() {
    ArrayWarp aw = new ArrayWarp(initData, 0, 2);
    assertThat(aw.size(), is(2));

    assertThat(aw.get(0), is(1));
    assertThat(aw.get(1), is(2));
  }
}
