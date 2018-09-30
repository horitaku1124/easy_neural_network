package com.github.horitaku1124.util;

public class MyNumArray {
    public int ndim;
    public long size;
    public int[] shape;
    public float[] internalData;
    public MyNumArray() {}

    public MyNumArray(float[] data) {
        ndim = 1;
        size = data.length;
        shape = new int[data.length];
        internalData = new float[(int) size];
        int index = 0;
        for (float aData : data) {
            internalData[index] = aData;
            index++;
        }
    }
    public MyNumArray(float[][] data) {
        ndim = 2;
        size = data.length * data[0].length;
        internalData = new float[(int) size];
        shape = new int[] {data.length, data[0].length};
        int index = 0;
        for (float[] aData : data) {
            for (float anAData : aData) {
                internalData[index] = anAData;
                index++;
            }
        }
    }

    public MyNumArray(float[][][] data) {
        ndim = 3;
        size = data.length * data[0].length * data[0][0].length;
        shape = new int[] {data.length, data[0].length, data[0][0].length};
        internalData = new float[(int) size];
        int index = 0;
        for (float[][] aData : data) {
            for (float[] anAData : aData) {
                for (float anAnAData : anAData) {
                    internalData[index] = anAnAData;
                    index++;
                }
            }
        }
    }

    public MyNumArray(int... shapeDefinition) {
        ndim = shapeDefinition.length;
        size = 1;
        for (int num: shapeDefinition) {
            size *= num;
        }
        shape = shapeDefinition;
        internalData = new float[(int) size];
    }

    public int size() {
        return (int)size;
    }

    public int layerLength(int dimension) {
        return shape[dimension];
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

    public void set(float value, int... offset) {
        internalData[offsetToIndex(offset)] = value;
    }

    public float get(int... offset) {
        return internalData[offsetToIndex(offset)];
    }

    public float SUMXMY2(MyNumArray target, int arrayIndex) {
        int thisOffset = arrayIndex * shape[1];
        int thatOffset = arrayIndex * target.shape[1];
        int limit = shape[1];
        float sum = 0;
        for (int i = 0;i < limit;i++) {
            sum += Math.pow(internalData[i + thisOffset] - target.internalData[i + thatOffset], 2);
        }
        return sum;
    }
}
