package com.github.horitaku1124.chapter4;

import com.github.horitaku1124.util.MyNumArray;

public class SolverTest4v2 {
    /** 隠れ層 */
    private static MyNumArray hiddenLayerWeights;
    /** 隠れ層-閾値 */
    private static MyNumArray hiddenLayerBias;
    /** 出力層 */
    private static MyNumArray outputLayerWeights;
    /** 出力層-閾値 */
    private static MyNumArray outputLayerBias;
    public static void setupData() {
        float[][][] hiddenLayers = new float[][][]{
                {
                        {0.0550f, 0.1660f, 0.1170f},
                        {0.0790f, 0.3330f, 0.1790f},
                        {0.1500f, 0.9230f, 0.1190f},
                        {0.9800f, 0.1110f, 0.1980f},
                },
                {
                        {0.0800f, 0.9120f, 0.1210f},
                        {0.2940f, 0.1760f, 0.2070f},
                        {0.3460f, 0.1200f, 0.2180f},
                        {0.1910f, 0.9720f, 0.0320f},
                },
                {
                        {0.0800f, 0.9120f, 0.1210f},
                        {0.2940f, 0.1760f, 0.2070f},
                        {0.3460f, 0.1200f, 0.2180f},
                        {0.1910f, 0.9720f, 0.0320f},
                }
        };
        hiddenLayerWeights = new MyNumArray(hiddenLayers);

        float[] hiddenThresholds = new float[]{0.9690f, 0.9170f, 0.9410f};
        hiddenLayerBias = new MyNumArray(hiddenThresholds);

        float[][] outputLayers = new float[][]{
                {0.1810f, 0.9230f, 0.0610f},
                {0.9870f, 0.1010f, 0.8420f}
        };
        outputLayerWeights = new MyNumArray(outputLayers);

        float[] outputThresholds = new float[] {1.00f, 0.94f};
        outputLayerBias = new MyNumArray(outputThresholds);

    }

    private static float targetFunction(MyNumArray inputImages, MyNumArray inputLabels) {
        final int hiddenLayer = 3;
        int imagesLength = inputImages.layerLength(0);
        MyNumArray resultArray = new MyNumArray(imagesLength, hiddenLayer);
        MyNumArray output11 = new MyNumArray(64, 3);
        float error = 0;
        for (int l = 0; l < imagesLength; l++) {
            for (int i = 0;i < hiddenLayer;i++) {
                float output = - inputImages.sumProductRank3x3(l, hiddenLayerWeights, i);
                output += hiddenLayerBias.get(i);
                output = 1.0f / (1.0f + (float)Math.exp(output));
                resultArray.set(output, l, i);
            }
            float A = resultArray.sumProductRank2x2(l, outputLayerWeights, 0);

            output11.set(
                    1.0f / (1.0f + (float)Math.exp(
                    (-A) + outputLayerBias.get(0))),
                    l, 0
            );
            output11.set(
                    1.0f / (1.0f + (float)Math.exp(
                            (-resultArray.sumProductRank2x2(l, outputLayerWeights, 1)+ outputLayerBias.get(1)))),
                    l, 1
            );

            error += inputLabels.SUMXMY2(output11, l);
        }
        return error;
    }


    public static void main(String[] args) {
        final int Loop = 5000;
        final float Step = 0.1f;
        final float h = 0.0001f;
        SolverTest4v2.setupData();

        MyNumArray inputImages = new MyNumArray(
                InputData.inputLayers.length,
                InputData.inputLayers[0].length,
                InputData.inputLayers[0][0].length);
        MyNumArray inputLabels = new MyNumArray(InputData.answerLayers);

        for (int i = 0;i < InputData.inputLayers.length;i++) {
            for (int j = 0;j < InputData.inputLayers[0].length;j++) {
                for (int k = 0;k < InputData.inputLayers[0][0].length;k++) {
                    inputImages.set(InputData.inputLayers[i][j][k], i, j, k);
                }
            }
        }

        System.out.println(targetFunction(inputImages, inputLabels));
        MyNumArray[] outputData = new MyNumArray[]{hiddenLayerWeights, hiddenLayerBias, outputLayerWeights, outputLayerBias};
        for(int b = 0;b < Loop;b++) {
            for (MyNumArray weight: outputData) {
                for (int k = 0;k < weight.size();k++) {
                    float data = weight.internalData[k];

                    float baseError = targetFunction(inputImages, inputLabels);
                    weight.internalData[k] = data + h;
                    float nextError = targetFunction(inputImages, inputLabels);
                    float dxdy = (nextError - baseError) / h;
                    data = data - Step * dxdy;

                    weight.internalData[k] = data;
                }
            }
            System.out.println("b=" + b + " error=" + targetFunction(inputImages, inputLabels));
            if (targetFunction(inputImages, inputLabels) < 0.01) {
                break;
            }
        }
    }
}
