package com.github.horitaku1124.b2chapter4;

import com.github.horitaku1124.util.MyNumArray;

import java.util.stream.IntStream;
import static java.util.stream.IntStream.range;

import static java.lang.System.out;

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
        outputLayers = new MyNumArray(new float[][] {
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

        for (int loop = 0;loop < 1;loop++) {

            MyNumArray SigmaArray = new MyNumArray(inputLength, hiddenLayers.layerLength(0));
            MyNumArray dCda3Array = new MyNumArray(inputLength, outputLayers.layerLength(0));
            MyNumArray dCda3δ3Array = new MyNumArray(inputLength, outputLayers.layerLength(0));

            MyNumArray globalA2array =  new MyNumArray(inputLength, hiddenLayers.layerLength(0));
            int page;
            page = 1;
            float totalErrorC = 0;
            for (int l = 0;l < inputLength;l++) {
//            out.println("page=" + page);
                // 変数値算出
//            l = 63;
                MyNumArray a2array =  new MyNumArray(hiddenLayers.layerLength(0));
                MyNumArray az2array =  new MyNumArray(hiddenLayers.layerLength(0));
//            out.print("隠れ層.z2i=  ");
                for (int i = 0;i < hiddenLayers.layerLength(0);i++) {
                    float z2 = hiddenLayers.sumProductRank3x3(i, InputData.inputLayers, l);
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

                MyNumArray z3array = new MyNumArray(outputLayers.layerLength(0));
                MyNumArray a3array = new MyNumArray(outputLayers.layerLength(0));
                MyNumArray s3array = new MyNumArray(outputLayers.layerLength(0));
                MyNumArray az3array = new MyNumArray(outputLayers.layerLength(0));
                float C = 0;
                for (int i = 0;i < outputLayers.layerLength(0);i++) {
                    float z3 = a2array.sumProductRank1x2(outputLayers, i) + outputBiases.get(i);
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
                C /= outputLayers.layerLength(0);
                MyNumArray HS = outputLayers.mmulti(s3array); // 隠れ層 - Σwδ3
//            out.print("δ2=");
                for (int i = 0;i < hiddenLayers.layerLength(0);i++) {
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
                for (int i = 0;i < outputLayers.layerLength(0);i++) {
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
                    hiddenLayers.layerLength(0),
                    hiddenLayers.layerLength(1),
                    hiddenLayers.layerLength(2)
            );
            // 2乗誤差の偏微分 隠れ層
            page = 0;
            for (int l = 0;l < inputLength;l++) {
                for (int i = 0; i < hiddenLayers.layerLength(0); i++) {
                    float sigma = SigmaArray.get(l, i);
//                out.println(page);
                    for (int j = 0; j < hiddenLayers.layerLength(1); j++) {
                        for (int k = 0; k < hiddenLayers.layerLength(2); k++) {
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

            // 隠れ層.勾配 ∂CT/∂w
            MyNumArray hiddenLayerDecent = new MyNumArray(
                    hiddenLayers.layerLength(0),
                    hiddenLayers.layerLength(1),
                    hiddenLayers.layerLength(2)
            );
            for (int i = 0; i < hiddenLayers.layerLength(0); i++) {
                for (int j = 0; j < hiddenLayers.layerLength(1); j++) {
                    for (int k = 0; k < hiddenLayers.layerLength(2); k++) {
                        float sum = 0;
                        for (int l = 0;l < inputLength;l++) {
                            sum += partialDeviationError.get(l, i, j, k);
                        }
                        hiddenLayerDecent.set(sum, i, j, k);
//                    System.out.println(sum);
                    }
                }
            }

            // ∂CT/∂b
            MyNumArray hiddenLayerDecent2 = new MyNumArray(hiddenLayers.layerLength(0));
            for (int i = 0;i < hiddenLayers.layerLength(0);i++) {
                float sum = 0;
                for (int j = 0;j < inputLength;j++) {
                    sum += SigmaArray.get(j, i);
                }
                hiddenLayerDecent2.set(sum, i);
            }

            MyNumArray outputLayerDecent = new MyNumArray(
                    inputLength,
                    outputLayers.layerLength(0),
                    outputLayers.layerLength(1)
            );
            for (int i = 0;i < inputLength;i++) {
//                out.println(" -- ");
                for (int j = 0;j < outputLayers.layerLength(1);j++) {
                    float value = globalA2array.get(i, j);
                    for (int k = 0;k < outputLayers.layerLength(0);k++) {
                        float a = value * dCda3δ3Array.get(i, k);
                        outputLayerDecent.set(a, i, k, j);
//                        out.print(a);
//                        out.print(", ");
                    }
//                    out.println("");
                }
            }
            MyNumArray outputLayerDecentParams = new MyNumArray(
                    outputLayers.layerLength(0),
                    outputLayers.layerLength(1)
            );
            for (int i = 0;i < outputLayers.layerLength(0);i++) {
                for (int j = 0;j < outputLayers.layerLength(1);j++) {
                    float sum = 0;
                    for (int l = 0;l < inputLength;l++) {
                        sum += outputLayerDecent.get(l, i, j);
                    }
                    outputLayerDecentParams.set(sum, i, j);
                    out.println(sum);
                }
            }
            out.println("totalErrorC=" + totalErrorC);


            // Copy For Next
            for (int i = 0; i < hiddenLayers.layerLength(0); i++) {
                for (int j = 0; j < hiddenLayers.layerLength(1); j++) {
                    for (int k = 0; k < hiddenLayers.layerLength(2); k++) {
                        float value = hiddenLayers.get(i, j, k) + hiddenLayerDecent.get(i, j, k) * learntRatio;
                        hiddenLayers.set(value, i, j, k);
                    }
                }
            }
            for (int i = 0;i < hiddenLayers.layerLength(0);i++) {
                float value = hiddenBiases.get(i) + hiddenLayerDecent2.get(i) * learntRatio;
                hiddenBiases.set(value, i);
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        setupData();
        System.out.println(targetFunction());
    }
}
