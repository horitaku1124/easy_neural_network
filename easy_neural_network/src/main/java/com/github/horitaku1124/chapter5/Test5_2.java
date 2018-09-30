package com.github.horitaku1124.chapter5;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.horitaku1124.util.MyNumArray;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.max;

public class Test5_2 {
    public static void main(String[] args) throws IOException {

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("result.json"));) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        OutputData2 resultData = mapper.readValue(sb.toString(), OutputData2.class);

        InputData2 inputData = Train5_2.setupInputData("./data1.txt");


        final int inputSize = inputData.inputLayers.layerLength(0);
        // 6x6の畳み込み層を3層、入力層分用意
        MyNumArray convolutions2 = new MyNumArray(inputSize, 3, 6, 6);

        // 畳込層
        for (int input = 0;input < inputSize;input++) {
            for (int f = 0;f < 3;f++) {
                for(int i = 0;i < 6;i++) {
                    for(int j = 0;j < 6;j++) {
                        float sum = 0;
                        for(int x = 0;x < 4;x++) {
                            for (int y = 0;y < 4;y++) {
                                sum += inputData.inputLayers.get(input, x + i, y + j) * resultData.convolutionFilters.get(f, x, y);
                            }
                        }
                        convolutions2.set(max(0.0f, sum - resultData.convolutionTheta.get(f)),
                                input, f, i, j
                        );
                    }
                }
            }
        }

        // プーリング層
        MyNumArray pooling2 = new MyNumArray(inputSize, 3, 3, 3);
        for (int input = 0;input < inputSize;input++) {
            for (int f = 0; f < 3; f++) {
                for(int i = 0;i < 3;i++) {
                    for (int j = 0; j < 3; j++) {
                        float f1 = convolutions2.get(input, f, i * 2, j * 2);
                        float f2 = convolutions2.get(input, f, i * 2, j * 2 + 1);
                        float f3 = convolutions2.get(input, f, i * 2 + 1, j * 2);
                        float f4 = convolutions2.get(input, f, i * 2 + 1, j * 2 + 1);
                        float max = max(max(f1, f2), max(f3, f4));
                        pooling2.set(max,
                                input, f, i, j);
                    }
                }
            }
        }

        // 出力層
        MyNumArray output2 = new MyNumArray(inputSize, 2);
        float Q0 = 0;
        int correct = 0;
        List<MyNumArray> outputLayers = Arrays.asList(resultData.outputLayer1, resultData.outputLayer2);
        for (int input = 0;input < inputSize;input++) {
            for (int z = 0;z < outputLayers.size();z++) {
                float sum = 0;
                for (int f = 0;f < 3;f++) {
                    for(int i = 0;i < 3;i++) {
                        for(int j = 0;j < 3;j++) {
                            sum += outputLayers.get(z).get(f, i, j) * pooling2.get(input, f, i, j);
                        }
                    }
                }
                output2.set((float) (1 / (1 + Math.exp(resultData.outputTheta.get(z) - sum))),
                        input, z
                );
            }
            float q = inputData.answerLayers.SUMXMY2(output2, input);
            Q0 += q;
            if (maxIndex(inputData.answerLayers, input) == maxIndex(output2, input)) {
                correct++;
            }
        }
        System.out.println("Q0=" + Q0);
        System.out.println("q=" + ((float)correct / inputSize));
    }

    private static int maxIndex(MyNumArray data, int input) {
        int index = 0;
        float max = 0;
        int len = data.layerLength(1);
        for (int i = 0;i < len;i++) {
            if (max < data.get(input, i)) {
                max = data.get(input, i);
                index = i;
            }
        }
        return index;
    }

    private static int maxIndex(float[] array) {
        int index = 0;
        float max = 0;
        for (int i = 0;i < array.length;i++) {
            if (max < array[i]) {
                max = array[i];
                index = i;
            }
        }
        return index;
    }
}
