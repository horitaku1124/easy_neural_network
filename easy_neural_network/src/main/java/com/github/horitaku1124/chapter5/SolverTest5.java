package com.github.horitaku1124.chapter5;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.horitaku1124.util.CalculationFunctions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.github.horitaku1124.chapter5.InputData.answerLayers;
import static com.github.horitaku1124.chapter5.InputData.inputLayers;
import static java.lang.Math.max;

public class SolverTest5 {
    private static OutputData resultData;

    public static float targetFunction() {

        // 6x6の畳み込み層を3層、入力層分用意
        float[][][][] convolutions = new float[inputLayers.length][3][6][6];

        // 畳込層
        for (int input = 0;input < inputLayers.length;input++) {
            for (int f = 0;f < 3;f++) {
                for(int i = 0;i < 6;i++) {
                    for(int j = 0;j < 6;j++) {
                        float sum = 0;
                        for(int x = 0;x < 4;x++) {
                            for (int y = 0;y < 4;y++) {
                                sum += inputLayers[input][x + i][y + j] * resultData.convolutionFilters[f][x][y];
                            }
                        }
                        convolutions[input][f][i][j] = max(0.0f, sum - resultData.convolutionTheta[f]);
                    }
                }
            }
        }

        // プーリング層
        float[][][][] pooling = new float[inputLayers.length][3][3][3];
        for (int input = 0;input < inputLayers.length;input++) {
            for (int f = 0; f < 3; f++) {
                for(int i = 0;i < 3;i++) {
                    for (int j = 0; j < 3; j++) {
                        float f1 = convolutions[input][f][i * 2][j * 2];
                        float f2 = convolutions[input][f][i * 2][j * 2 + 1];
                        float f3 = convolutions[input][f][i * 2 + 1][j * 2];
                        float f4 = convolutions[input][f][i * 2 + 1][j * 2 + 1];
                        pooling[input][f][i][j] = max(max(f1, f2), max(f3, f4));
                    }
                }
            }
        }


        // 出力層
        float[][] output = new float[inputLayers.length][2];
        float Q0 = 0;
        for (int input = 0;input < inputLayers.length;input++) {
            List<float[][][]> outputLayers = Arrays.asList(resultData.outputLayer1, resultData.outputLayer2);
            for (int z = 0;z < outputLayers.size();z++) {
                float sum = 0;
                for (int f = 0;f < 3;f++) {
                    for(int i = 0;i < 3;i++) {
                        for(int j = 0;j < 3;j++) {
                            sum += outputLayers.get(z)[f][i][j] * pooling[input][f][i][j];
                        }
                    }
                }
                output[input][z] = (float) (1 / (1 + Math.exp(resultData.outputTheta[z] - sum)));
            }
            float q = CalculationFunctions.SUMXMY2(answerLayers[input], output[input]);
            Q0 += q;
        }
        return Q0;
    }

    public static void main(String[] args) throws IOException {
        final int Loop = 100;
        final float Step = 0.1f;
        final float h = 0.001f;
        resultData = new OutputData("./data1.txt");

        for(int b = 0;b < Loop;b++) {
            for (long i = 0;i < resultData.allLength;i++) {
                float data = resultData.get(i);

                float baseError = targetFunction();
                resultData.set(i, data + h);
                float nextError = targetFunction();
                float dxdy = (nextError - baseError) / h;
//                System.out.println("dxdy=" + dxdy);
                data = data - Step * dxdy;
                resultData.set(i, data);
            }
            System.out.println("b=" + b + " error=" + targetFunction());
            if (targetFunction() < 0.01) {
                break;
            }
        }

        System.out.println(targetFunction());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(resultData);

        try (FileWriter filewriter = new FileWriter(new File("result.json"), true);) {
            filewriter.write(json);
        }

//        OutputData hoge = mapper.readValue(json, OutputData.class);

        System.out.println(json);
//        System.out.println(hoge);
    }
}
