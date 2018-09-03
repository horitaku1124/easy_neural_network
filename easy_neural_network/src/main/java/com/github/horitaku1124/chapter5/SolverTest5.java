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

    static int w1;
    static int w2 ;
    static int w3;
    static int w11;
    static long hiddenLength;
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



//        w1 = convolutionFilters[0][0].length;
//        w2 = convolutionFilters[0].length;
//        w3 = w1 * w2;
//        w11 = outputLayers.length * outputLayers[0].length;
//        hiddenLength = w3 * convolutionFilters.length;
//        allLength = hiddenLength + hiddenThresholds.length + w11 + outputThresholds.length;
    }
    public static void main(String[] args) {
        setupData();
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

        System.out.println(Q0);
    }
}
