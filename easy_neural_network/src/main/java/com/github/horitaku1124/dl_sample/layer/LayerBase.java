package com.github.horitaku1124.dl_sample.layer;

import com.github.horitaku1124.util.MyNumArray;

public abstract class LayerBase {
    private MyNumArray weights;
    private MyNumArray biases;
    private MyNumArray hWeights;
    private MyNumArray hBiases;


    private MyNumArray weightGradient;


    public LayerBase(int upper, int n) {
        weights = MyNumArray.rand(upper, n);
        biases = MyNumArray.rand(n);

        hWeights = MyNumArray.zeros(upper, n);
        hWeights.broadcastAdd(1e-8f);
        hBiases = MyNumArray.zeros(n);
        hBiases.broadcastAdd(1e-8f);
    }

    public void update(float eta) {
//        hWeights += weightGradient * weightGradient;
//        weights +=
    }
}
