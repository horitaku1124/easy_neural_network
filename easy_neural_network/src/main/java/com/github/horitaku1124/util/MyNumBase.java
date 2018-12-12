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
    public MyNumBase(R[][] data) {
        internalData = initializeData(data);
        ndim = 2;
        size = internalData.length;
        shape = new int[]{data.length, data[0].length};
    }
    abstract R[] initializeData(R[] data);
    abstract R[] initializeData(R[][] data);


    public int layerLength(int dimension) {
        return shape[dimension];
    }

    public int offsetToIndex(int... offset) {
        int index = -1;
        if (ndim == 1) {
            index = offset[0];
        } else if (ndim == 2) {
            index = offset[0] * shape[1] + offset[1];
        } else if (ndim == 3) {
            index = offset[0] * shape[1] * shape[2] + offset[1] * shape[2] + offset[2];
        } else if (ndim == 4) {
            index = offset[0] * shape[1] * shape[2] * shape[3] +
                    offset[1] * shape[2] * shape[3] +
                    offset[2] * shape[3] +
                    offset[3];
        }
        if (index >= internalData.length) {
            for (int i = 0;i < offset.length;i++) {
                System.out.println("offset[" + i + "]=" + offset[i]);
            }
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return index;
    }
}
