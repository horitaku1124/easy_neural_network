package com.github.horitaku1124.util;

public class ActivationFunction {
    public static float sigmoid(float x) {
        return 1.0f / (1.0f + (float)Math.exp(-x));
    }
}
