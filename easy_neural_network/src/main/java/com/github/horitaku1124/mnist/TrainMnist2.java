package com.github.horitaku1124.mnist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.horitaku1124.b2chapter4.OutputData;
import com.github.horitaku1124.util.Calculation;
import com.github.horitaku1124.util.MnistReader;
import com.github.horitaku1124.util.MyNumArray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TrainMnist2 {
    private final static float LearntRatio = 0.1f;
    private static final int LABEL_LENGTH = 10;
    private static final int LAYER_NUM = 20;
    private static void setUpData(OutputData dist) {
        dist.hiddenWeights = MyNumArray.rand(LAYER_NUM, 28, 28);
        dist.hiddenBiases = MyNumArray.rand(LAYER_NUM);
        dist.outputWeights = MyNumArray.rand(LABEL_LENGTH, LAYER_NUM);
        dist.outputBiases = MyNumArray.rand(LABEL_LENGTH);
    }

    private static float targetFunction(MyNumArray imagesLA, MyNumArray labelsLA, OutputData dist) {
//        final int inputLength = imagesLA.layerLength(0);
        final int inputLength = 2000;

        MyNumArray SigmaArray = new MyNumArray(inputLength, dist.hiddenWeights.layerLength(0));
        MyNumArray dCda3Array = new MyNumArray(inputLength, dist.outputWeights.layerLength(0));
        MyNumArray dCda3δ3Array = new MyNumArray(inputLength, dist.outputWeights.layerLength(0));

        MyNumArray globalA2array =  new MyNumArray(inputLength, dist.hiddenWeights.layerLength(0));
        float totalErrorC = 0;
        for (int l = 0;l < inputLength;l++) {
//            l = 63;
            MyNumArray a2array =  new MyNumArray(dist.hiddenWeights.layerLength(0));
            MyNumArray az2array =  new MyNumArray(dist.hiddenWeights.layerLength(0));
//            out.print("隠れ層.z2i=  ");
            for (int i = 0; i < dist.hiddenWeights.layerLength(0); i++) {
                float z2 = dist.hiddenWeights.sumProductRank3x3(i, imagesLA, l);
                z2 += dist.hiddenBiases.get(i);
//                out.print(z2);
//                out.print(",");
                float a2 = Calculation.calculateOutputA(z2); // a2i
                a2array.set(a2, i);
                globalA2array.set(a2, l, i);
                float az2 = a2 * (1 - a2); // a'(z2i)
                az2array.set(az2, i);
//                System.out.print(z2 + " ");
            }
//            out.println("");

            MyNumArray z3array = new MyNumArray(dist.outputWeights.layerLength(0));
            MyNumArray a3array = new MyNumArray(dist.outputWeights.layerLength(0));
            MyNumArray s3array = new MyNumArray(dist.outputWeights.layerLength(0));
            MyNumArray az3array = new MyNumArray(dist.outputWeights.layerLength(0));
            float C = 0;
            for (int i = 0; i < dist.outputWeights.layerLength(0); i++) {
                float z3 = a2array.sumProductRank1x2(dist.outputWeights, i) + dist.outputBiases.get(i);
                z3array.set(z3, i);
                float a3 = Calculation.calculateOutputA(z3);
                float az3 = a3 * (1 - a3);
                a3array.set(a3, i);
                az3array.set(az3, i);
                float c = labelsLA.get(l, i) - a3;

                // δ算出
                float PD = a3 - labelsLA.get(l, i); // 偏微分
                float S3 = az3 * PD;
                s3array.set(S3, i);
                C += c * c;
            }
            C /= dist.outputWeights.layerLength(0);
            MyNumArray HS = dist.outputWeights.mmulti(s3array); // 隠れ層 - Σwδ3
//            out.print("δ2=");
            for (int i = 0; i < dist.hiddenWeights.layerLength(0); i++) {
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
            for (int i = 0; i < dist.outputWeights.layerLength(0); i++) {
                // ∂C/∂a3
                float value = a3array.get(i) - labelsLA.get(l, i);
                dCda3Array.set(value, l, i); // TODO no need to store?
                float sigma = value * az3array.get(i); // δ3
//                out.println(sigma);
                dCda3δ3Array.set(sigma, l, i);
            }

//            System.out.println("");
        }

        // 2乗誤差の偏微分
        MyNumArray partialDeviationError = new MyNumArray(
                inputLength,
                dist.hiddenWeights.layerLength(0),
                dist.hiddenWeights.layerLength(1),
                dist.hiddenWeights.layerLength(2)
        );
        // 2乗誤差の偏微分 隠れ層
        for (int l = 0;l < inputLength;l++) {
            for (int i = 0; i < dist.hiddenWeights.layerLength(0); i++) {
                float sigma = SigmaArray.get(l, i);
//                out.println(page);
                for (int j = 0; j < dist.hiddenWeights.layerLength(1); j++) {
                    for (int k = 0; k < dist.hiddenWeights.layerLength(2); k++) {
                        float value = sigma * imagesLA.get(l, j, k); // ∂C/∂w
                        // ∂C/∂w
                        partialDeviationError.set(value, l, i, j, k);
//                        out.print(value);
//                        out.print(" , ");
                    }
//                    out.println("");
                }
            }
        }
        // 2乗誤差の偏微分 出力層

        MyNumArray outputBiasDiff = new MyNumArray(dist.outputBiases.layerLength(0));
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
                dist.hiddenWeights.layerLength(0),
                dist.hiddenWeights.layerLength(1),
                dist.hiddenWeights.layerLength(2)
        );
        for (int i = 0; i < dist.hiddenWeights.layerLength(0); i++) {
            for (int j = 0; j < dist.hiddenWeights.layerLength(1); j++) {
                for (int k = 0; k < dist.hiddenWeights.layerLength(2); k++) {
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
        MyNumArray hiddenBiasDiff = new MyNumArray(dist.hiddenWeights.layerLength(0));
        for (int i = 0; i < dist.hiddenWeights.layerLength(0); i++) {
            float sum = 0;
            for (int j = 0;j < inputLength;j++) {
                sum += SigmaArray.get(j, i);
            }
            hiddenBiasDiff.set(sum, i);
//                out.println(sum);
        }

        MyNumArray hiddenWeightsDiff = new MyNumArray(
                inputLength,
                dist.outputWeights.layerLength(0),
                dist.outputWeights.layerLength(1)
        );
        for (int i = 0;i < inputLength;i++) {
//                out.println(" -- ");
            for (int j = 0; j < dist.outputWeights.layerLength(1); j++) {
                float value = globalA2array.get(i, j);
                for (int k = 0; k < dist.outputWeights.layerLength(0); k++) {
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
                dist.outputWeights.layerLength(0),
                dist.outputWeights.layerLength(1)
        );
        for (int i = 0; i < dist.outputWeights.layerLength(0); i++) {
            for (int j = 0; j < dist.outputWeights.layerLength(1); j++) {
                float sum = 0;
                for (int l = 0;l < inputLength;l++) {
                    sum += hiddenWeightsDiff.get(l, i, j);
                }
                outputWeightsDiff.set(sum, i, j);
//                    out.println(sum);
            }
        }

//            System.out.println("totalErrorC=" + totalErrorC);


        // Copy For Next
        for (int i = 0; i < dist.hiddenWeights.layerLength(0); i++) {
            for (int j = 0; j < dist.hiddenWeights.layerLength(1); j++) {
                for (int k = 0; k < dist.hiddenWeights.layerLength(2); k++) {
                    float value = dist.hiddenWeights.get(i, j, k) - hiddenWeightsDecent.get(i, j, k) * LearntRatio;
//                        out.println(hiddenWeights.get(i, j, k) + ", " + hiddenWeightsDecent.get(i, j, k) + ", " + value);
                    dist.hiddenWeights.set(value, i, j, k);
                }
            }
        }

        for (int i = 0;i < dist.hiddenBiases.layerLength(0);i++) {
            float value = dist.hiddenBiases.get(i) - hiddenBiasDiff.get(i) * LearntRatio;
            dist.hiddenBiases.set(value, i);
        }
        for (int i = 0; i < outputWeightsDiff.layerLength(0); i++) {
            for (int j = 0; j < outputWeightsDiff.layerLength(1); j++) {
                float value = dist.outputWeights.get(i, j) - outputWeightsDiff.get(i, j) * LearntRatio;
                dist.outputWeights.set(value, i, j);
            }
        }
        for (int i = 0;i < outputBiasDiff.layerLength(0);i++) {
            float value = dist.outputBiases.get(i) - outputBiasDiff.get(i) * LearntRatio;
            dist.outputBiases.set(value, i);
        }

        return totalErrorC;
    }
    public static void main(String[] args) throws IOException {
        MnistReader reader = new MnistReader();
        MyNumArray images = reader.readImageData("./dataset/t10k-images-idx3-ubyte");
        MyNumArray labels = reader.readLabelData("./dataset/t10k-labels-idx1-ubyte");
        OutputData dist = new OutputData();
        setUpData(dist);
        for (int loop = 0;loop < 50;loop++) {
            System.out.println(targetFunction(images, labels, dist));
        }

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dist);

        try (FileWriter filewriter = new FileWriter(new File("result3.json"))) {
            filewriter.write(json);
        }
    }
}
