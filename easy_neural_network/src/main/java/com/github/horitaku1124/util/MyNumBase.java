package com.github.horitaku1124.util;

public abstract class MyNumBase<R> {
    public int ndim;
    public long size;
    public int[] shape;
    public R[] internalData;

    public MyNumBase(R[] data) {
        ndim = 1;
        size = data.length;
        shape = new int[]{data.length};
        internalData = initializeData(data);
    }
    abstract R[] initializeData(R[] data);
}
