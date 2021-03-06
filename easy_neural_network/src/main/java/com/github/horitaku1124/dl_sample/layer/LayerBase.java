package com.github.horitaku1124.dl_sample.layer;

import com.github.horitaku1124.util.MyNumArray;
import static com.github.horitaku1124.util.MyNumArray.broadcastDivide;

public abstract class LayerBase {
    protected MyNumArray weights;
    protected MyNumArray biases;
    protected MyNumArray hWeights;
    protected MyNumArray hBiases;


    protected MyNumArray weightGradient;
    protected MyNumArray biasGradient;

    public LayerBase(int upper, int n) {
        weights = MyNumArray.rand(upper, n);
        biases = MyNumArray.rand(n);

        hWeights = MyNumArray.zeros(upper, n);
        hWeights.broadcastAdd(1e-8f);
        hBiases = MyNumArray.zeros(n);
        hBiases.broadcastAdd(1e-8f);
    }

    public void update(float eta) {
        hWeights.add(weightGradient.multiply(weightGradient));
        weights = weights.minus(
                broadcastDivide(eta, MyNumArray.sqrt(hWeights)).multiply(weightGradient)
        );

        hBiases.add(biasGradient.multiply(biasGradient));

        biases = biases.minus(
                broadcastDivide(eta, MyNumArray.sqrt(hBiases)).multiply(biasGradient)
        );
    }

    abstract public void forward(MyNumArray x);
    abstract public void backward(MyNumArray t);
}
