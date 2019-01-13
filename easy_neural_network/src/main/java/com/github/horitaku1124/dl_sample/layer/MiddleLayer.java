package com.github.horitaku1124.dl_sample.layer;

import com.github.horitaku1124.util.MyNumArray;

/**
 * 中間層
 */
public class MiddleLayer extends LayerBase {
    public MyNumArray x;
    public MyNumArray u;
    public MyNumArray y;
    public MyNumArray grad_w;
    public MyNumArray grad_b;
    public MyNumArray grad_x;
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
        this.grad_w = x.transpose().dot(delta);
        this.grad_b = MyNumArray.sum(delta);
        this.grad_x = delta.dot(weights.transpose());
    }
}
