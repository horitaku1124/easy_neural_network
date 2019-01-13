package com.github.horitaku1124.dl_sample.layer;

import com.github.horitaku1124.util.MyNumArray;

/**
 * 出力層
 */
public class OutputLayer extends LayerBase {
    public MyNumArray x;
    public MyNumArray y;
    public MyNumArray grad_w;
    public MyNumArray grad_b;
    public MyNumArray grad_x;

    public OutputLayer(int upper, int n) {
        super(upper, n);
    }

    @Override
    public void forward(MyNumArray x) {
        this.x = x;
        MyNumArray u = x.dot(weights);
        u = u.add(biases);

        MyNumArray x1 = MyNumArray.exp(u);
        // ソフトマックス関数
        MyNumArray x2 = MyNumArray.sum(MyNumArray.exp(u), 1, true);
        this.y = x1.divide(x2);
    }

    @Override
    public void backward(MyNumArray t) {
        MyNumArray delta = y.minus(t);
        grad_w = x.transpose().dot(delta);
        grad_b = MyNumArray.sum(delta, 0, false);
        grad_x = delta.dot(weights.transpose());
    }
}
