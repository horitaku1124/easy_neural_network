package com.github.horitaku1124.util;


public class ArrayWarp {
  public int[] getArray() {
    return array;
  }

  public void setArray(int[] array) {
    this.array = array;
  }

  public int getStart() {
    return start;
  }

  public void setStart(int start) {
    this.start = start;
  }

  public int getStop() {
    return stop;
  }

  public void setStop(int stop) {
    this.stop = stop;
  }

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
