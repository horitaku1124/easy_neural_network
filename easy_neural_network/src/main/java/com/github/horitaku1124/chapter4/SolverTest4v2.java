package com.github.horitaku1124.chapter4;

import com.github.horitaku1124.util.MyNumArray;

import static com.github.horitaku1124.util.ActivationFunction.sigmoid;

public class SolverTest4v2 {
    /** 隠れ層 */
    private static MyNumArray hiddenLayerWeights;
    /** 隠れ層-閾値 */
    private static MyNumArray hiddenLayerBias;
    /** 出力層 */
    private static MyNumArray outputLayerWeights;
    /** 出力層-閾値 */
    private static MyNumArray outputLayerBias;

    private final static int DATA_WIDTH = 3;
    private final static int DATA_HEIGHT = 4;
    private final static int HIDDEN_LAYER_NUM = 3;
    private final static int OUTPUT_LAYER_NUM = 2;

    private static void setupData() {
        hiddenLayerWeights = MyNumArray.rand(HIDDEN_LAYER_NUM, DATA_WIDTH, DATA_HEIGHT);
        hiddenLayerBias = MyNumArray.rand(HIDDEN_LAYER_NUM);
        outputLayerWeights = MyNumArray.rand(OUTPUT_LAYER_NUM, HIDDEN_LAYER_NUM);
        outputLayerBias = MyNumArray.rand(OUTPUT_LAYER_NUM);
    }

    private static float targetFunction(MyNumArray inputImages, MyNumArray inputLabels) {
        int imagesLength = inputImages.layerLength(0);
        MyNumArray resultArray = new MyNumArray(imagesLength, HIDDEN_LAYER_NUM);
        MyNumArray output11 = new MyNumArray(imagesLength, OUTPUT_LAYER_NUM);
        float error = 0;
        for (int l = 0; l < imagesLength; l++) {
            for (int i = 0;i < HIDDEN_LAYER_NUM;i++) {
                float x = inputImages.sumProductRank3x3(l, hiddenLayerWeights, i)
                        - hiddenLayerBias.get(i);
                resultArray.set(sigmoid(x), l, i);
            }
            for (int i = 0;i < OUTPUT_LAYER_NUM;i++) {
                float x = resultArray.sumProductRank2x2(l, outputLayerWeights, i)
                        - outputLayerBias.get(i);
                output11.set(sigmoid(x), l, i);
            }

            error += inputLabels.SUMXMY2(output11, l);
        }
        return error;
    }


    public static void main(String[] args) {
        final int Loop = 5000;
        final float Step = 0.1f;
        final float h = 0.0001f;
        setupData();

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
