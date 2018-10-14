package com.github.horitaku1124.b2chapter4;

import com.github.horitaku1124.util.CalculationFunctions;
import com.github.horitaku1124.util.MyNumArray;

public class SolverTestB24 {
    /** 隠れ層 */
    private static MyNumArray hiddenLayers;
    /** 隠れ層 - バイアス */
    private static MyNumArray hiddenBiases;
    /** 出力層 */
    private static MyNumArray outputLayers;
    /** 出力層 - バイアス */
    private static MyNumArray outputBiases;

    public static void setupData() {
        hiddenLayers = new MyNumArray(new float[][][]{
                {
                        {0.490f, 0.348f, 0.073f},
                        {0.837f, -0.071f, -3.617f},
                        {-0.536f, -0.023f, -1.717f},
                        {-1.456f, -0.556f, 0.852f},
                },
                {
                        {0.442f, -0.537f, 1.008f},
                        {1.072f, -0.733f, 0.823f},
                        {-0.453f, -0.014f, -0.027f},
                        {-0.427f, 1.876f, -2.305f},
                },
                {
                        {0.654f, -1.389f, 1.246f},
                        {0.057f, -0.183f, -0.743f},
                        {-0.461f, 0.331f, 0.449f},
                        {-1.296f, 1.569f, -0.471f},
                }
        });
        hiddenBiases = new MyNumArray(new float[]{-0.1850f, 0.5256f, -1.1686f});
        outputLayers = new MyNumArray(new float[][] {
                {0.388f, 0.803f, 0.029f},
                {0.025f, -0.790f, 1.553f},
        });
        outputBiases = new MyNumArray(new float[]{-1.438f, -1.379f});
    }

    private static float calculateOutputA(float z2) {
        return 1.0f / (1.0f + (float)Math.exp(-z2));
    }

    public static float targetFunction() {
        for (int l = 0;l < InputData.inputLayers.layerLength(0);l++) {
            // 変数値算出
            MyNumArray a2array =  new MyNumArray(hiddenLayers.layerLength(0));
            MyNumArray az2array =  new MyNumArray(hiddenLayers.layerLength(0));
            for (int i = 0;i < hiddenLayers.layerLength(0);i++) {
                float z2 = hiddenLayers.sumProductRank3x3(i, InputData.inputLayers, l);
                z2 += hiddenBiases.get(i);
                float a2 = calculateOutputA(z2);
                a2array.set(a2, i);
                float a22 = a2 * (1 - a2);
                az2array.set(a22, i);
                System.out.print(z2 + " ");
            }
            System.out.println("");

            MyNumArray z3array = new MyNumArray(outputLayers.layerLength(0));
            MyNumArray a3array = new MyNumArray(outputLayers.layerLength(0));
            MyNumArray s3array = new MyNumArray(outputLayers.layerLength(0));
            float C = 0;
            for (int i = 0;i < outputLayers.layerLength(0);i++) {
                float z3 = a2array.sumProductRank1x2(outputLayers, i) + outputBiases.get(i);
                z3array.set(z3, i);
                float a3 = calculateOutputA(z3);
                float az3 = a3 * (1 - a3);
                a3array.set(a3, i);
                float c = InputData.answerLayers.get(l, i) - a3;

                // δ算出
                float PD = a3 - InputData.answerLayers.get(l, i); // 偏微分
                float S3 = az3 * PD;
                s3array.set(S3, i);
                C += c * c;
            }
            C /= outputLayers.layerLength(0);
            MyNumArray HS = outputLayers.mmulti(s3array); // 隠れ層 - Σwδ3
            MyNumArray SigmaArray = new MyNumArray(hiddenLayers.layerLength(0));
            for (int i = 0;i < hiddenLayers.layerLength(0);i++) {
                float sigma = az2array.get(i) * HS.get(i);
                SigmaArray.set(sigma, i);
            }

            // 2乗誤差の偏微分
            MyNumArray partialDeviationError =  new MyNumArray(
                    hiddenLayers.layerLength(0),
                    hiddenLayers.layerLength(1),
                    hiddenLayers.layerLength(2)
            );

            for (int i = 0;i < hiddenLayers.layerLength(0);i++) {
                float sigma = SigmaArray.get(i);
                for (int j = 0;j < hiddenLayers.layerLength(1);j++) {
                    for (int k = 0;k < hiddenLayers.layerLength(2);k++) {
                        float answer = InputData.inputLayers.get(l, j, k);
                        float value = sigma * answer;
                        partialDeviationError.set(value, i, j, k);
                    }
                }
            }

            System.out.print("  C=" + C);
            System.out.println("");
        }
        return 0;
    }

    public static void main(String[] args) {
        setupData();
        System.out.println(targetFunction());
    }
}
