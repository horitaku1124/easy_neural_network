package com.github.horitaku1124.chapter5;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.horitaku1124.util.MyNumArray;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.max;

public class Train5_2 {
    public static OutputData resultData;

    private static float targetFunction(final InputData2 inputData) {

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
                                sum += inputData.inputLayers.get(input, x + i, y + j) * resultData.convolutionFilters[f][x][y];
                            }
                        }
                        convolutions2.set(max(0.0f, sum - resultData.convolutionTheta[f]),
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
        List<float[][][]> outputLayers = Arrays.asList(resultData.outputLayer1, resultData.outputLayer2);
        for (int input = 0;input < inputSize;input++) {
            for (int z = 0;z < outputLayers.size();z++) {
                float sum = 0;
                for (int f = 0;f < 3;f++) {
                    for(int i = 0;i < 3;i++) {
                        for(int j = 0;j < 3;j++) {
                            sum += outputLayers.get(z)[f][i][j] * pooling2.get(input, f, i, j);
                        }
                    }
                }
                output2.set((float) (1 / (1 + Math.exp(resultData.outputTheta[z] - sum))),
                        input, z
                );
            }
            float q = inputData.answerLayers.SUMXMY2(output2, input);
            Q0 += q;
        }
        return Q0;
    }

    public static InputData2 setupInputData(String filePath) {
        InputData2 inputData = new InputData2();
        final int width = 9;
        final int height = 9;

        List<String[]> lines = new ArrayList<>();
        try (FileReader fr = new FileReader(filePath);
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split("\t");
                // process the line.
                lines.add(row);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        int inputLength = lines.get(0).length / width;
        inputData.inputLayers = new MyNumArray(inputLength, width, height);
        inputData.answerLayers = new MyNumArray(inputLength, 2);

        int index = 0;
        for (int i = 0;i < lines.get(0).length;i += width,index++) {
            for (int j = 0;j < height;j++) {
                String[] row = lines.get(j);
                for (int k = 0;k < width;k++) {
                    inputData.inputLayers.set(
                            Float.parseFloat(row[k + i]),
                            index, j, k
                    );
                }

            }
            String[] lastLine = lines.get(lines.size() - 1);
            inputData.answerLayers.set(Float.parseFloat(lastLine[i]), index, 0);
            inputData.answerLayers.set(Float.parseFloat(lastLine[i + 1]), index, 1);
        }
        return inputData;
    }

    public static void main(String[] args) throws IOException {
        final int Loop = 100;
        final float Step = 0.2f;
        final float h = 0.01f;
        InputData2 inputData = setupInputData("./data1.txt");
        resultData = new OutputData();
        resultData.initializeData();

        for(int b = 0;b < Loop;b++) {
            for (long i = 0;i < resultData.allLength;i++) {
                float data = resultData.get(i);
                float baseError = targetFunction(inputData);
                resultData.set(i, data + h);
                float nextError = targetFunction(inputData);
                float dxdy = (nextError - baseError) / h;
                data = data - Step * dxdy;
                resultData.set(i, data);
//                System.out.println("baseError=" + baseError + " nextError=" + nextError + " data=" + data);
            }
            System.out.println("b=" + b + " error=" + targetFunction(inputData));
            if (targetFunction(inputData) < 0.01) {
                break;
            }
        }

        System.out.println(targetFunction(inputData));
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(resultData);

        try (FileWriter filewriter = new FileWriter(new File("result.json"))) {
            filewriter.write(json);
        }

//        OutputData hoge = mapper.readValue(json, OutputData.class);

        System.out.println(json);
//        System.out.println(hoge);
    }
}
