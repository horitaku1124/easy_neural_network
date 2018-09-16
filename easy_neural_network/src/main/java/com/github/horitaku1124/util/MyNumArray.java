package com.github.horitaku1124.util;

public class MyNumArray {
    public int ndim;
    public long size;
    public int[] shape;
    public float[] internalData;
    public MyNumArray(int... shapeDefinition) {
        ndim = shapeDefinition.length;
        size = 1;
        for (int num: shapeDefinition) {
            size *= num;
        }
        shape = shapeDefinition;
        internalData = new float[(int) size];
    }
    public void dataCopy(byte[] data) {
        dataCopy(data, 0);
    }
    public void dataCopy(byte[] data, int index) {
        for (int i = 0;i < data.length;i++) {
            internalData[index + i] = (float)(data[i] & 0xff);
        }
    }

    public int offsetToIndex(int... offset) {
        if (ndim == 1) {
            return offset[0];
        } else if (ndim == 2) {
            return offset[0] * shape[1] + offset[1];
        } else if (ndim == 3) {
            return offset[0] * shape[1] * shape[2] + offset[1] * shape[2] + offset[2];
        } else if (ndim == 4) {
            return
                    offset[0] * shape[1] * shape[2] * shape[3] +
                    offset[1] * shape[2] * shape[3] +
                    offset[2] * shape[3] +
                    offset[3];
        }
        return -1;
    }

    public void set(float value, int... offset) {
        internalData[offsetToIndex(offset)] = value;
    }

    public float get(int... offset) {
        return internalData[offsetToIndex(offset)];
    }
}
