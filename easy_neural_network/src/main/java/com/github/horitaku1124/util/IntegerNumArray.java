package com.github.horitaku1124.util;

public class IntegerNumArray extends MyNumBase<Integer> {
    public IntegerNumArray(Integer[] data) {
        super(data);
    }

    @Override
    Integer[] initializeData(Integer[] data) {
        Integer[] data2 = new Integer[data.length];
        int index = 0;
        for (Integer aData : data) {
            data2[index] = aData;
            index++;
        }
        return data2;
    }

    @Override
    Integer[] initializeData(Integer[][] data) {
        int size = data.length * data[0].length;
        Integer[] data2  = new Integer[size];
        int index = 0;
        for (Integer[] aData : data) {
            for (Integer anAData : aData) {
                data2[index] = anAData;
                index++;
            }
        }

        return data2;
    }
}
