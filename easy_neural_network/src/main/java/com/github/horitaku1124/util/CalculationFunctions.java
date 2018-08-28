package com.github.horitaku1124.util;

public class CalculationFunctions {
    public static float SUMXMY2(float[] correct, float[] answer) {
        int x = correct.length;
        float sum = 0;
        for (int i = 0;i < x;i++) {
            sum += Math.pow(correct[i] - answer[i], 2);
        }
        return sum;
    }

    public static float sumProduct(float[] inputLayers, float[] hiddenLayers) {
        float sum = 0;
        int x = inputLayers.length;
        for(int i = 0;i < x;i++) {
            sum += inputLayers[i] * hiddenLayers[i];
        }
        return sum;
    }
    public static float sumProduct(int[][] inputLayers, float[][] hiddenLayers) {
        float sum = 0;
        int x = inputLayers.length;
        int y = inputLayers[0].length;
        for(int i = 0;i < x;i++) {
            for (int j = 0;j < y;j++) {
                sum += inputLayers[i][j] * hiddenLayers[i][j];
            }
        }
        return sum;
    }
}
