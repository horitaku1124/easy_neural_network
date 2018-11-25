package com.github.horitaku1124.util;

public class Calculation {
    public static float calculateOutputA(float z2) {
        return 1.0f / (1.0f + (float)Math.exp(-z2));
    }
}
