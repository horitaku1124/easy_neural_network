package com.github.horitaku1124.util;

import lombok.Data;

@Data
public class ArrayWarp {
  private int[] array;
  private int start;
  private int stop;
  public ArrayWarp(int[] array, int start, int stop) {
    this.array = array;
    this.start = start;
    this.stop = stop;
  }
  public int size() {
    return stop - start;
  }

  public int get(int index) {
    int internal_index = index + start;
    if (internal_index >= stop) {
      throw new IndexOutOfBoundsException(index + "(" +internal_index + ") is out of index");
    }
    return array[internal_index];
  }
}
