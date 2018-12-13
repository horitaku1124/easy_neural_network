package com.github.horitaku1124.util;

public class FloatNumArray extends MyNumBase<Float> {
    public FloatNumArray(Float[] data) {
        super(data);
    }

    @Override
    Float[] initializeData(int size) {
        return new Float[size];
    }

    @Override
    Float[] initializeData(Float[] data) {
        Float[] data2 = new Float[data.length];
        int index = 0;
        for (Float aData : data) {
            data2[index] = aData;
            index++;
        }
        return data2;
    }

    @Override
    Float[] initializeData(Float[][] data) {
        int size = data.length * data[0].length;
        Float[] data2  = new Float[size];
        int index = 0;
        for (Float[] aData : data) {
            for (Float anAData : aData) {
                data2[index] = anAData;
                index++;
            }
        }

        return data2;
    }

    @Override
    MyNumBase<Float> newInstance(int... shapeDefinition) {
        return null;
    }
}
