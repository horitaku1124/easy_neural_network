package com.github.horitaku1124.dl_sample.layer;

import com.github.horitaku1124.util.MyNumArray;

public class MiddleLayer extends LayerBase {
    MyNumArray x;
    MyNumArray u;
    MyNumArray y;
    public MiddleLayer(int upper, int n) {
        super(upper, n);
    }

    @Override
    public void forward(MyNumArray x) {
        this.x = x;
//        this.u = MyNumArray.dot
    }

    @Override
    public void backward(MyNumArray t) {

    }
}
