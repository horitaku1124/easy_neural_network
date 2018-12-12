package com.github.horitaku1124.util;

public class FloatNumArray extends MyNumBase<Float> {
    public FloatNumArray(Float[] data) {
        super(data);
    }

    @Override
    Float[] initializeData(Float[] data) {
        return new Float[0];
    }
}
