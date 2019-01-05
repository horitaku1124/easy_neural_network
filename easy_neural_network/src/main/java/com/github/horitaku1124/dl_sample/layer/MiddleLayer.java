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
        this.u = x.dot(this.weights);
        this.y = this.u.where(n -> n <= 0f ? 0f : n);
    }

    @Override
    public void backward(MyNumArray grad_y) {
        MyNumArray u = this.u.where(n -> n <= 0.f ? 0 : 1f);
        MyNumArray delta = grad_y.multiply(u);
        x.transpose()
    }
}
