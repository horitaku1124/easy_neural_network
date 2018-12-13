package com.github.horitaku1124.util;

public abstract class MyNumBase<R> {
    public int ndim;
    public long size;
    public int[] shape;
    public R[] internalData;


    public MyNumBase(int... shapeDefinition) {
        ndim = shapeDefinition.length;
        size = 1;
        for (int num: shapeDefinition) {
            size *= num;
        }
        shape = shapeDefinition;
        internalData = initializeData((int) size);
    }

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
    abstract R[] initializeData(int size);
    abstract R[] initializeData(R[] data);
    abstract R[] initializeData(R[][] data);
    abstract MyNumBase newInstance(int... shapeDefinition);


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

    public void set(R value, int... offset) {
        internalData[offsetToIndex(offset)] = value;
    }

    public R get(int... offset) {
        return internalData[offsetToIndex(offset)];
    }

    public MyNumBase<R> reshape(int... shape) {
        int length = 1;
        for (int i: shape) {
            length *= i;
        }
        if (length != size) {
            throw new RuntimeException("shape size mismatch");
        }
        MyNumBase<R> newArray = newInstance(shape);
        if (size >= 0) System.arraycopy(this.internalData,
                0, newArray.internalData, 0, Math.toIntExact(size));
        return newArray;
    }
}
