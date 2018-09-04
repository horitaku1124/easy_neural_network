package com.github.horitaku1124.chapter5;

import com.github.horitaku1124.chapter4.SolverTest4;
import com.github.horitaku1124.util.CalculationFunctions;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.max;

import static com.github.horitaku1124.chapter5.InputData.inputLayers;
import static com.github.horitaku1124.chapter5.InputData.answerLayers;

public class SolverTest5 {
    /** 畳み込み層 - フィルター */
    private static float[][][] convolutionFilters;
    /** 畳み込み層 - θ */
    private static float[] convolutionTheta;
    /** 出力層 o1 */
    private static float[][][] outputLayer1;
    /** 出力層 o2 */
    private static float[][][] outputLayer2;
    /** 出力層 - θ */
    private static float[] outputTheta;

    static int w1, w2, w3, w4;
    static int x1, x2, x3;
    static int o11, o12, o13, o14;
    static int o21, o22, o23, o24;
    static long convolutionLength, output1Length, output2Length;

    static long allLength;


    public static void setupData() {
        final int width = 9;
        final int height = 9;
        List<String[]> lines = new ArrayList<>();
        try (FileReader fr = new FileReader("./data1.txt");
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
        InputData.inputLayers = new float[inputLength][width][height];
        InputData.answerLayers = new float[inputLength][2];

        int index = 0;
        for (int i = 0;i < lines.get(0).length;i += width,index++) {
            for (int j = 0;j < height;j++) {
                String[] row = lines.get(j);
                for (int k = 0;k < width;k++) {
                    InputData.inputLayers[index][j][k] = Float.parseFloat(row[k + i]);
                }

            }
            String[] lastLine = lines.get(lines.size() - 1);
            InputData.answerLayers[index][0] = Float.parseFloat(lastLine[i]);
            InputData.answerLayers[index][1] = Float.parseFloat(lastLine[i + 1]);
        }


        convolutionFilters = new float[][][] {
                {
                        {1.0f, 1.0f, 1.0f, 1.0f},
                        {1.0f, 1.0f, 1.0f, 1.0f},
                        {1.0f, 1.0f, 1.0f, 1.0f},
                        {1.0f, 1.0f, 1.0f, 1.0f},
                },
                {
                        {0.5f, 0.5f, 0.5f, 0.5f},
                        {0.5f, 0.5f, 0.5f, 0.5f},
                        {0.5f, 0.5f, 0.5f, 0.5f},
                        {0.5f, 0.5f, 0.5f, 0.5f},
                },
                {
                        {0.1f, 0.1f, 0.1f, 0.1f},
                        {0.1f, 0.1f, 0.1f, 0.1f},
                        {0.1f, 0.1f, 0.1f, 0.1f},
                        {0.1f, 0.1f, 0.1f, 0.1f},
                }
        };

        convolutionTheta = new float[]{0, 1.38f, 0.87f};

        outputLayer1 = new float[][][] {
                {
                        {0f, 0f, 0f},
                        {0f, 0f, 0f},
                        {0f, 0f, 0f},
                },
                {
                        {0f, 0f, 0f},
                        {0f, 0f, 0f},
                        {0f, 0f, 0f},
                },
                {
                        {1.01f, 0f, 0f},
                        {0f, 0f, 0f},
                        {0f, 0f, 0f},
                }
        };

        outputLayer2 = new float[][][] {

                {
                        {0f, 0f, 0f},
                        {0f, 0f, 0.43f},
                        {0f, 0f, 2.27f},
                },
                {
                        {0f, 0f, 0f},
                        {1.10f, 0.29f, 0f},
                        {1.04f, 2.19f, 0f},
                },
                {
                        {0f, 0f, 0f},
                        {0f, 0f, 0.23f},
                        {0f, 0f, 0.95f},
                }
        };

        outputTheta = new float[]{2.48f, 2.27f};

        w1 = convolutionFilters[0][0].length;
        w2 = convolutionFilters[0].length;
        w3 = convolutionFilters.length;
        w4 = w1 * w2;
        x1 = outputLayer1[0][0].length;
        x2 = outputLayer1[0].length;
        x3 = outputLayer1.length;
        convolutionLength = w4 * w3;
        o11 = outputLayer1[0][0].length;
        o12 = outputLayer1[0].length;
        o13 = outputLayer1.length;
        o14 = o11 * o12;
        output1Length = o14 * o13;
        o21 = outputLayer2[0][0].length;
        o22 = outputLayer2[0].length;
        o23 = outputLayer2.length;
        o24 = o21 * o22;
        output2Length = o24 * o23;

        allLength = convolutionLength
                + convolutionTheta.length
                + o14 * o13
                + o24 * o23
                + outputTheta.length;
    }

    public static void set(long index, float value) {
        float[][][] hl = SolverTest5.convolutionFilters;
        if (index < convolutionLength) {
            int x = (int) (index / w4);
            int y = (int) ((index - (x * w4)) / w2);
            int z = (int) (index % w2);
            hl[x][y][z] = value;
            return;
        }
        if (index < convolutionLength + convolutionTheta.length) {
            convolutionTheta[(int) (index - convolutionLength)] = value;
            return;
        }
        long nextIndex = convolutionLength + convolutionTheta.length;
        if (index < nextIndex + output1Length) {
            index = index - nextIndex;
            int x = (int) (index / o14);
            int y = (int) ((index - (x * o14)) / o12);
            int z = (int) (index % o12);

            outputLayer1[x][y][z] = value;
            return;
        }
        nextIndex += output1Length;

        if (index < nextIndex + output2Length) {
            index = index - nextIndex;
            int x = (int) (index / o24);
            int y = (int) ((index - (x * o24)) / o22);
            int z = (int) (index % o22);

            outputLayer2[x][y][z] = value;
            return;
        }
        nextIndex += output2Length;
        int lastIndex = (int) (index - nextIndex);
        outputTheta[lastIndex] = value;
    }
    public static float get(long index) {
        float[][][] hl = SolverTest5.convolutionFilters;
        if (index < convolutionLength) {
            int x = (int) (index / w4);
            int y = (int) ((index - (x * w4)) / w2);
            int z = (int) (index % w2);
            return hl[x][y][z];
        }
        if (index < convolutionLength + convolutionTheta.length) {
            return convolutionTheta[(int) (index - convolutionLength)];
        }
        long nextIndex = convolutionLength + convolutionTheta.length;
        if (index < nextIndex + output1Length) {
            index = index - nextIndex;
            int x = (int) (index / o14);
            int y = (int) ((index - (x * o14)) / o12);
            int z = (int) (index % o12);

            return outputLayer1[x][y][z];
        }
        nextIndex += output1Length;

        if (index < nextIndex + output2Length) {
            index = index - nextIndex;
            int x = (int) (index / o24);
            int y = (int) ((index - (x * o24)) / o22);
            int z = (int) (index % o22);

            return outputLayer2[x][y][z];
        }
        nextIndex += output2Length;
        int lastIndex = (int) (index - nextIndex);
        return outputTheta[lastIndex];
    }

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
                                sum += inputLayers[input][x + i][y + j] * convolutionFilters[f][x][y];
                            }
                        }
                        convolutions[input][f][i][j] = max(0.0f, sum - convolutionTheta[f]);
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
            List<float[][][]> outputLayers = Arrays.asList(outputLayer1, outputLayer2);
            for (int z = 0;z < outputLayers.size();z++) {
                float sum = 0;
                for (int f = 0;f < 3;f++) {
                    for(int i = 0;i < 3;i++) {
                        for(int j = 0;j < 3;j++) {
                            sum += outputLayers.get(z)[f][i][j] * pooling[input][f][i][j];
                        }
                    }
                }
                output[input][z] = (float) (1 / (1 + Math.exp(outputTheta[z] - sum)));
            }
            float q = CalculationFunctions.SUMXMY2(answerLayers[input], output[input]);
            Q0 += q;
        }
        return Q0;
    }

    public static void main(String[] args) {
        final int Loop = 5000;
        final float Step = 0.1f;
        final float h = 0.001f;
        setupData();

        for(int b = 0;b < Loop;b++) {
            for (long i = 0;i < allLength;i++) {
                float data = get(i);

                float baseError = targetFunction();
                set(i, data + h);
                float nextError = targetFunction();
                float dxdy = (nextError - baseError) / h;
//                System.out.println("dxdy=" + dxdy);
                data = data - Step * dxdy;
                set(i, data);
            }
            System.out.println("b=" + b + " error=" + targetFunction());
            if (targetFunction() < 0.01) {
                break;
            }
        }

        System.out.println(targetFunction());
    }
}
