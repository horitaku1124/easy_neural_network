package com.github.horitaku1124.mnist;

import com.github.horitaku1124.util.BinaryReader;
import com.github.horitaku1124.util.CalculationFunctions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.github.horitaku1124.mnist.InputData.answerLayers;
import static com.github.horitaku1124.mnist.InputData.inputLayers;
import static java.lang.Math.max;

public class TrainMnist {
    public static OutputData resultData;
    private static float targetFunction() {

        // 6x6の畳み込み層を3層、入力層分用意
        float[][][][] convolutions = new float[inputLayers.length][3][24][24];

        // 畳込層
        for (int input = 0;input < inputLayers.length;input++) {
            for (int f = 0;f < 3;f++) {
                for(int i = 0;i < 24;i++) {
                    for(int j = 0;j < 24;j++) {
                        float sum = 0;
                        for(int x = 0;x < 5;x++) {
                            for (int y = 0;y < 5;y++) {
                                sum += inputLayers[input][x + i][y + j] * resultData.convolutionFilters[f][x][y];
                            }
                        }
                        convolutions[input][f][i][j] = max(0.0f, sum - resultData.convolutionTheta[f]);
                    }
                }
            }
        }

        // プーリング層
        float[][][][] pooling = new float[inputLayers.length][3][12][12];
        for (int input = 0;input < inputLayers.length;input++) {
            for (int f = 0; f < 3; f++) {
                for(int i = 0;i < 12;i++) {
                    for (int j = 0; j < 12; j++) {
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
                    for(int i = 0;i < 12;i++) {
                        for(int j = 0;j < 12;j++) {
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
    public static void setupInputData(String inputData, String answerData) throws IOException {
        BinaryReader br = BinaryReader.getBigEndianReader();
        float[][][] allImageData;
        try (FileInputStream fs = new FileInputStream(inputData)) {
            byte[] buf = new byte[16];
            fs.read(buf);
            long magicNumber = br.bytesToLong(buf, 0);
            int imageNumber = (int) br.bytesToLong(buf, 4);
            int rowsNumber = (int) br.bytesToLong(buf, 8);
            int columnsNumber = (int) br.bytesToLong(buf, 12);
            System.out.println(magicNumber);
            System.out.println(imageNumber);
            System.out.println(rowsNumber);
            System.out.println(columnsNumber);
            int imageSize = rowsNumber * columnsNumber;
            allImageData = new float[imageNumber][rowsNumber][columnsNumber];
            byte[] img = new byte[imageSize];
            for (int i = 0;i < imageNumber / 10;i++) {
                fs.read(img);
                for (int x = 0;x < rowsNumber;x++) {
                    for (int y = 0;y < columnsNumber;y++) {
                        int b = img[x * rowsNumber + y];
                        allImageData[i][x][y] = (float)(b & 0xff) / 255.0f;
                    }
                }
            }
        }
        float[][] answerLayer;
        try (FileInputStream fs = new FileInputStream(answerData)) {
            byte[] buf = new byte[8];
            fs.read(buf);
            long magicNumber = br.bytesToLong(buf, 0);
            int labelNumber = (int) br.bytesToLong(buf, 4);
            answerLayer = new float[labelNumber][2];
            System.out.println(magicNumber);
            System.out.println(labelNumber);
            byte[] labels = new byte[labelNumber];
            fs.read(labels);
            for (int i = 0;i < labels.length / 10;i++) {
                if (labels[i] < 2) {
                    answerLayer[i][labels[i]] = 1f;
                }
            }
        }
        InputData.inputLayers = allImageData;
        InputData.answerLayers = answerLayer;
    }
    public static void main(String[] args) throws IOException {
        final int Loop = 100;
        final float Step = 0.2f;
        final float h = 0.1f;
        setupInputData("./dataset/t10k-images-idx3-ubyte" ,"./dataset/t10k-labels-idx1-ubyte");

        resultData = new OutputData();
        resultData.initializeData();
        for(int b = 0;b < Loop;b++) {
            for (long i = 0;i < resultData.allLength;i++) {
                float data = resultData.get(i);
                float baseError = targetFunction();
                resultData.set(i, data + h);
                float nextError = targetFunction();
                float dxdy = (nextError - baseError) / h;
                data = data - Step * dxdy;
                resultData.set(i, data);
                System.out.println("baseError=" + baseError + " nextError=" + nextError + " data=" + data);
            }
            System.out.println("b=" + b + " error=" + targetFunction());
            if (targetFunction() < 0.01) {
                break;
            }
        }
    }
}
