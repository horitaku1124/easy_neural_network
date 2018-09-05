package com.github.horitaku1124.chapter5;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.horitaku1124.util.CalculationFunctions;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static com.github.horitaku1124.chapter5.InputData.answerLayers;
import static com.github.horitaku1124.chapter5.InputData.inputLayers;
import static com.github.horitaku1124.chapter5.Train5.setupInputData;
import static java.lang.Math.max;

public class Test5 {
    public static void main(String[] args) throws IOException {

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("result.json"));) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        OutputData resultData = mapper.readValue(sb.toString(), OutputData.class);

        setupInputData("./data1.txt");


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
        int correct = 0;
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
            if (maxIndex(answerLayers[input]) == maxIndex(output[input])) {
                correct++;
            }
        }
        System.out.println("Q0=" + Q0);
        System.out.println("q=" + ((float)correct / inputLayers.length));
    }


    private static int maxIndex(float[] array) {
        int index = 0;
        float max = 0;
        for (int i = 1;i < array.length;i++) {
            if (max < array[i]) {
                max = array[i];
                index = i;
            }
        }
        return index;
    }
}
