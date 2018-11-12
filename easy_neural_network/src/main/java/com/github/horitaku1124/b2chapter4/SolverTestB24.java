package com.github.horitaku1124.b2chapter4;

import com.github.horitaku1124.util.MyNumArray;

import static java.lang.System.out;

public class SolverTestB24 {
    /** 隠れ層 - 重み */
    private static MyNumArray hiddenWeights;
    /** 隠れ層 - バイアス */
    private static MyNumArray hiddenBiases;
    /** 出力層 - 重み */
    private static MyNumArray outputWeights;
    /** 出力層 - バイアス */
    private static MyNumArray outputBiases;

    public static void setupData() {
        hiddenWeights = new MyNumArray(new float[][][]{
                {
                        {0.433838676f, 0.595730963f, 0.149209681f},
                        {0.974617381f, 0.806329200f, 0.227836943f},
                        {0.273935529f, 0.853599467f, 0.208697733f},
                        {0.879950849f, 0.527596257f, 0.731243881f},
                },
                {
                        {0.221867463f, 0.059964301f, 0.204826309f},
                        {0.487251664f, 0.812223610f, 0.049787422f},
                        {0.267727281f, 0.777761226f, 0.986606082f},
                        {0.733365330f, 0.013537667f, 0.264984982f},
                },
                {
                        {0.111487346f, 0.894505519f, 0.817180551f},
                        {0.204597564f, 0.606646197f, 0.740661278f},
                        {0.123397674f, 0.048778029f, 0.467294204f},
                        {0.971597327f, 0.706960089f, 0.813331242f},
                }
        });
        hiddenBiases = new MyNumArray(new float[]{0.345505068f, 0.757954383f, 0.046302824f});
        outputWeights = new MyNumArray(new float[][] {
                {0.742699777f, 0.212714517f, 0.307502359f},
                {0.049143412f, 0.560292649f, 0.447766059f},
        });
        outputBiases = new MyNumArray(new float[]{0.936020788f, 0.281364005f});
    }

    private static float calculateOutputA(float z2) {
        return 1.0f / (1.0f + (float)Math.exp(-z2));
    }

    public static float targetFunction() {
        final float learntRatio = 0.2f;
        final int inputLength = InputData.inputLayers.layerLength(0);

        for (int loop = 0;loop < 50;loop++) {
            MyNumArray SigmaArray = new MyNumArray(inputLength, hiddenWeights.layerLength(0));
            MyNumArray dCda3Array = new MyNumArray(inputLength, outputWeights.layerLength(0));
            MyNumArray dCda3δ3Array = new MyNumArray(inputLength, outputWeights.layerLength(0));

            MyNumArray globalA2array =  new MyNumArray(inputLength, hiddenWeights.layerLength(0));
            int page;
            page = 1;
            float totalErrorC = 0;
            for (int l = 0;l < inputLength;l++) {
//            out.println("page=" + page);
                // 変数値算出
//            l = 63;
                MyNumArray a2array =  new MyNumArray(hiddenWeights.layerLength(0));
                MyNumArray az2array =  new MyNumArray(hiddenWeights.layerLength(0));
//            out.print("隠れ層.z2i=  ");
                for (int i = 0; i < hiddenWeights.layerLength(0); i++) {
                    float z2 = hiddenWeights.sumProductRank3x3(i, InputData.inputLayers, l);
                    z2 += hiddenBiases.get(i);
//                out.print(z2);
//                out.print(",");
                    float a2 = calculateOutputA(z2); // a2i
                    a2array.set(a2, i);
                    globalA2array.set(a2, l, i);
                    float az2 = a2 * (1 - a2); // a'(z2i)
                    az2array.set(az2, i);
//                System.out.print(z2 + " ");
                }
//            out.println("");

                MyNumArray z3array = new MyNumArray(outputWeights.layerLength(0));
                MyNumArray a3array = new MyNumArray(outputWeights.layerLength(0));
                MyNumArray s3array = new MyNumArray(outputWeights.layerLength(0));
                MyNumArray az3array = new MyNumArray(outputWeights.layerLength(0));
                float C = 0;
                for (int i = 0; i < outputWeights.layerLength(0); i++) {
                    float z3 = a2array.sumProductRank1x2(outputWeights, i) + outputBiases.get(i);
                    z3array.set(z3, i);
                    float a3 = calculateOutputA(z3);
                    float az3 = a3 * (1 - a3);
                    a3array.set(a3, i);
                    az3array.set(az3, i);
                    float c = InputData.answerLayers.get(l, i) - a3;

                    // δ算出
                    float PD = a3 - InputData.answerLayers.get(l, i); // 偏微分
                    float S3 = az3 * PD;
                    s3array.set(S3, i);
                    C += c * c;
                }
                C /= outputWeights.layerLength(0);
                MyNumArray HS = outputWeights.mmulti(s3array); // 隠れ層 - Σwδ3
//            out.print("δ2=");
                for (int i = 0; i < hiddenWeights.layerLength(0); i++) {
                    float sigma = az2array.get(i) * HS.get(i); // δ2
                    SigmaArray.set(sigma, l, i);
//                out.print(sigma);
//                out.print(", ");
                }
                totalErrorC += C;
//            out.println("");

//            System.out.println(" C=" + C);

                // δ算出
                // 出力層
                for (int i = 0; i < outputWeights.layerLength(0); i++) {
                    // ∂C/∂a3
                    float value = a3array.get(i) - InputData.answerLayers.get(l, i);
                    dCda3Array.set(value, l, i); // TODO no need to store?
                    float sigma = value * az3array.get(i); // δ3
//                out.println(sigma);
                    dCda3δ3Array.set(sigma, l, i);
                }

                page += 4;
//            System.out.println("");
            }

            // 2乗誤差の偏微分
            MyNumArray partialDeviationError = new MyNumArray(
                    InputData.inputLayers.layerLength(0),
                    hiddenWeights.layerLength(0),
                    hiddenWeights.layerLength(1),
                    hiddenWeights.layerLength(2)
            );
            // 2乗誤差の偏微分 隠れ層
            page = 0;
            for (int l = 0;l < inputLength;l++) {
                for (int i = 0; i < hiddenWeights.layerLength(0); i++) {
                    float sigma = SigmaArray.get(l, i);
//                out.println(page);
                    for (int j = 0; j < hiddenWeights.layerLength(1); j++) {
                        for (int k = 0; k < hiddenWeights.layerLength(2); k++) {
                            float answer = InputData.inputLayers.get(l, j, k);
                            float value = sigma * answer; // ∂C/∂w
                            // ∂C/∂w
                            partialDeviationError.set(value, l, i, j, k);
//                        out.print(value);
//                        out.print(" , ");
                        }
//                    out.println("");
                    }
                    page += 1;
//                out.println(" -- ");
                }
                page += 1;
            }
            // 2乗誤差の偏微分 出力層

            MyNumArray outputBiasDiff = new MyNumArray(outputBiases.layerLength(0));
            for (int i = 0;i < outputBiasDiff.layerLength(0);i++) {
                float sum = 0;
                for (int l = 0;l < inputLength;l++) {
                    sum += dCda3δ3Array.get(l, i);
                }
//                out.println(sum);
                outputBiasDiff.set(sum, i);
            }



            // 勾配
            // 隠れ層 -  ∂CT/∂w
            MyNumArray hiddenWeightsDecent = new MyNumArray(
                    hiddenWeights.layerLength(0),
                    hiddenWeights.layerLength(1),
                    hiddenWeights.layerLength(2)
            );
            for (int i = 0; i < hiddenWeights.layerLength(0); i++) {
                for (int j = 0; j < hiddenWeights.layerLength(1); j++) {
                    for (int k = 0; k < hiddenWeights.layerLength(2); k++) {
                        float sum = 0;
                        for (int l = 0;l < inputLength;l++) {
                            sum += partialDeviationError.get(l, i, j, k);
                        }
                        hiddenWeightsDecent.set(sum, i, j, k);
//                        System.out.println(sum);
                    }
                }
            }

            // 隠れ層 - ∂CT/∂b
            MyNumArray hiddenBiasDiff = new MyNumArray(hiddenWeights.layerLength(0));
            for (int i = 0; i < hiddenWeights.layerLength(0); i++) {
                float sum = 0;
                for (int j = 0;j < inputLength;j++) {
                    sum += SigmaArray.get(j, i);
                }
                hiddenBiasDiff.set(sum, i);
//                out.println(sum);
            }

            MyNumArray hiddenWeightsDiff = new MyNumArray(
                    inputLength,
                    outputWeights.layerLength(0),
                    outputWeights.layerLength(1)
            );
            for (int i = 0;i < inputLength;i++) {
//                out.println(" -- ");
                for (int j = 0; j < outputWeights.layerLength(1); j++) {
                    float value = globalA2array.get(i, j);
                    for (int k = 0; k < outputWeights.layerLength(0); k++) {
                        float a = value * dCda3δ3Array.get(i, k);
                        hiddenWeightsDiff.set(a, i, k, j);
//                        out.print(a);
//                        out.print(", ");
                    }
//                    out.println("");
                }
            }

            // 出力層 - ∂CT/∂w
            MyNumArray outputWeightsDiff = new MyNumArray(
                    outputWeights.layerLength(0),
                    outputWeights.layerLength(1)
            );
            for (int i = 0; i < outputWeights.layerLength(0); i++) {
                for (int j = 0; j < outputWeights.layerLength(1); j++) {
                    float sum = 0;
                    for (int l = 0;l < inputLength;l++) {
                        sum += hiddenWeightsDiff.get(l, i, j);
                    }
                    outputWeightsDiff.set(sum, i, j);
//                    out.println(sum);
                }
            }

            out.println("totalErrorC=" + totalErrorC);


            // Copy For Next
            for (int i = 0; i < hiddenWeights.layerLength(0); i++) {
                for (int j = 0; j < hiddenWeights.layerLength(1); j++) {
                    for (int k = 0; k < hiddenWeights.layerLength(2); k++) {
                        float value = hiddenWeights.get(i, j, k) - hiddenWeightsDecent.get(i, j, k) * learntRatio;
//                        out.println(hiddenWeights.get(i, j, k) + ", " + hiddenWeightsDecent.get(i, j, k) + ", " + value);
                        hiddenWeights.set(value, i, j, k);
                    }
                }
            }

            for (int i = 0;i < hiddenBiases.layerLength(0);i++) {
                float value = hiddenBiases.get(i) - hiddenBiasDiff.get(i) * learntRatio;
                hiddenBiases.set(value, i);
            }
            for (int i = 0; i < outputWeightsDiff.layerLength(0); i++) {
                for (int j = 0; j < outputWeightsDiff.layerLength(1); j++) {
                    float value = outputWeights.get(i, j) - outputWeightsDiff.get(i, j) * learntRatio;
                    outputWeights.set(value, i, j);
                }
            }
            for (int i = 0;i < outputBiasDiff.layerLength(0);i++) {
                float value = outputBiases.get(i) - outputBiasDiff.get(i) * learntRatio;
                outputBiases.set(value, i);
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        setupData();
        System.out.println(targetFunction());
    }
}
