package com.github.horitaku1124.chapter4;

import com.github.horitaku1124.util.CalculationFunctions;

public class SolverTest4 {
    /** 隠れ層 */
    private static float[][][] hiddenLayers;
    /** 隠れ層-閾値 */
    private static float[] hiddenThresholds;
    /** 出力層 */
    private static float[][] outputLayers;
    /** 出力層-域値 */
    private static float[] outputThresholds;
    public static void setupData() {
        hiddenLayers = new float[3][3][4];
        hiddenLayers[0] = new float[][] {
                {0.0550f, 0.1660f, 0.1170f},
                {0.0790f, 0.3330f, 0.1790f},
                {0.1500f, 0.9230f, 0.1190f},
                {0.9800f, 0.1110f, 0.1980f},
        };
        hiddenLayers[1] = new float[][] {
                {0.0800f, 0.9120f, 0.1210f},
                {0.2940f, 0.1760f, 0.2070f},
                {0.3460f, 0.1200f, 0.2180f},
                {0.1910f, 0.9720f, 0.0320f},
        };
        hiddenLayers[2] = new float[][] {
                {0.9980f, 0.1640f, 0.9280f},
                {0.8920f, 0.9730f, 0.1080f},
                {0.9360f, 0.1170f, 0.0890f},
                {0.0380f, 0.0640f, 0.1300f},
        };

        hiddenThresholds = new float[]{0.9690f, 0.9170f, 0.9410f};


        outputLayers = new float[2][3];
        outputLayers[0] = new float[] {0.1810f, 0.9230f, 0.0610f};
        outputLayers[1] = new float[] {0.9870f, 0.1010f, 0.8420f};

        outputThresholds = new float[] {1.00f, 0.94f};

    }
    private static String convertToString(float[] data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0;i < data.length;i++) {
            sb.append(data[i]);
            if (i < data.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    private static String convertToString(int[][] data) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : data) {
            for (int i = 0;i < row.length;i++) {
                sb.append(row[i]);
                if (i < row.length - 1) {
                    sb.append(",");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private static float calculateOutputY(int[][] inputLayers, float[][] hiddenLayers, float threshold) {
        return 1.0f / (1.0f + (float)Math.exp(
                (-CalculationFunctions.sumProduct(inputLayers, hiddenLayers) + threshold)
        ));
    }
    private static float calculateOutputZ(float[] outputY, float[] outputLayers, float outputThreshold) {
        return 1.0f / (1.0f + (float)Math.exp(
                (-CalculationFunctions.sumProduct(outputY, outputLayers) + outputThreshold)
        ));
    }

    public static float targetFunction() {

        float[][] outputYResult = new float[64][3];
        float error = 0;
        for (int i = 0; i < InputData.inputLayers.length; i++) {
            for (int j = 0;j < 3;j++) {
                float output = calculateOutputY(
                        InputData.inputLayers[i], SolverTest4.hiddenLayers[j], SolverTest4.hiddenThresholds[j]);
                outputYResult[i][j] = output;
            }
            float[] output1 = new float[]{
                    calculateOutputZ(outputYResult[i], SolverTest4.outputLayers[0], SolverTest4.outputThresholds[0]),
                    calculateOutputZ(outputYResult[i], SolverTest4.outputLayers[1], SolverTest4.outputThresholds[1])
            };
            error += CalculationFunctions.SUMXMY2(InputData.answerLayers[i], output1);
        }
        return error;
    }

    public static void main(String[] args) {
        final int Loop = 2000;
        final float Step = 0.2f;
        SolverTest4.setupData();
        System.out.println(targetFunction());
        for(int b = 0;b < 50;b++) {

            for (int i = 0;i < SolverTest4.hiddenLayers.length;i++) {
                for (int j = 0;j < SolverTest4.hiddenLayers[i].length;j++) {
                    for (int k = 0;k < SolverTest4.hiddenLayers[i][j].length;k++) {
                        float data = SolverTest4.hiddenLayers[i][j][k];
                        SolverTest4.hiddenLayers[i][j][k] = data + 1;
                        float positiveError = targetFunction();
                        SolverTest4.hiddenLayers[i][j][k] = data - 1;
                        float negativeError = targetFunction();
                        for(int a = 0;a < Loop;a++) {
                            float startError = targetFunction();
                            if (negativeError < positiveError) {
                                SolverTest4.hiddenLayers[i][j][k] = data - Step;
                            } else {
                                SolverTest4.hiddenLayers[i][j][k] = data + Step;
                            }
                            float afterError = targetFunction();
                            if (startError < afterError - 0.01) {
                                break;
                            }
                        }
                        System.out.println(targetFunction());
                    }
                }
            }
            for (int i = 0;i < SolverTest4.outputLayers.length;i++) {
                for (int j = 0;j < SolverTest4.outputLayers[i].length;j++) {
                    float data = SolverTest4.outputLayers[i][j];
                    SolverTest4.outputLayers[i][j] = data + 1;
                    float positiveError = targetFunction();
                    SolverTest4.outputLayers[i][j] = data - 1;
                    float negativeError = targetFunction();
                    for(int a = 0;a < Loop;a++) {
                        float startError = targetFunction();
                        if (negativeError < positiveError) {
                            SolverTest4.outputLayers[i][j] = data - Step;
                        } else {
                            SolverTest4.outputLayers[i][j] = data + Step;
                        }
                        float afterError = targetFunction();
                        if (startError < afterError - 0.01) {
                            break;
                        }
                    }
                    System.out.println(targetFunction());
                }
            }
            for (int i = 0;i < SolverTest4.hiddenThresholds.length;i++) {
                float data = SolverTest4.hiddenThresholds[i];
                SolverTest4.hiddenThresholds[i] = data + 1;
                float positiveError = targetFunction();
                SolverTest4.hiddenThresholds[i] = data - 1;
                float negativeError = targetFunction();
                for(int a = 0;a < Loop;a++) {
                    float startError = targetFunction();
                    if (negativeError < positiveError) {
                        SolverTest4.hiddenThresholds[i] = data - Step;
                    } else {
                        SolverTest4.hiddenThresholds[i] = data + Step;
                    }
                    float afterError = targetFunction();
                    if (startError < afterError - 0.01) {
                        break;
                    }
                }
                System.out.println(targetFunction());
            }
            for (int i = 0;i < SolverTest4.outputThresholds.length;i++) {
                float data = SolverTest4.outputThresholds[i];
                SolverTest4.outputThresholds[i] = data + 1;
                float positiveError = targetFunction();
                SolverTest4.outputThresholds[i] = data - 1;
                float negativeError = targetFunction();
                for(int a = 0;a < Loop;a++) {
                    float startError = targetFunction();
                    if (negativeError < positiveError) {
                        SolverTest4.outputThresholds[i] = data - Step;
                    } else {
                        SolverTest4.outputThresholds[i] = data + Step;
                    }
                    float afterError = targetFunction();
                    if (startError < afterError - 0.01) {
                        break;
                    }
                }
                System.out.println(targetFunction());
            }
            System.out.println("b=" + b + " error=" + targetFunction());
        }
        System.out.println("LastError=" + targetFunction());
    }
}
