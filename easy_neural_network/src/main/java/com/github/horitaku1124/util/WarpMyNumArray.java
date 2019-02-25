package com.github.horitaku1124.util;

public class WarpMyNumArray extends MyNumArray {
  private MyNumArray numArray;
  private ArrayWarp indexAw;
  public WarpMyNumArray(MyNumArray numArray, ArrayWarp indexAw) {
    this.numArray = numArray;
    this.indexAw = indexAw;
  }
}
