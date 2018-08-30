package com.github.horitaku1124.chapter4;

import com.github.horitaku1124.util.CalculationFunctions;

public class SolverTest4 {
    /** 隠れ層 */
    private static float[][][] hiddenLayers;
    /** 隠れ層-閾値 */
    private static float[] hiddenThresholds;
    /** 出力層 */
    private static float[][] outputLayers;
    /** 出力層-閾値 */
    private static float[] outputThresholds;
    public static void setupData() {
        SolverTest4.hiddenLayers = new float[][][]{
                {
                        {0.0550f, 0.1660f, 0.1170f},
                        {0.0790f, 0.3330f, 0.1790f},
                        {0.1500f, 0.9230f, 0.1190f},
                        {0.9800f, 0.1110f, 0.1980f},
                },
                {
                        {0.0800f, 0.9120f, 0.1210f},
                        {0.2940f, 0.1760f, 0.2070f},
                        {0.3460f, 0.1200f, 0.2180f},
                        {0.1910f, 0.9720f, 0.0320f},
                },
                {
                        {0.0800f, 0.9120f, 0.1210f},
                        {0.2940f, 0.1760f, 0.2070f},
                        {0.3460f, 0.1200f, 0.2180f},
                        {0.1910f, 0.9720f, 0.0320f},
                }
        };

        hiddenThresholds = new float[]{0.9690f, 0.9170f, 0.9410f};

        outputLayers = new float[][]{
                {0.1810f, 0.9230f, 0.0610f},
                {0.9870f, 0.1010f, 0.8420f}
        };

        outputThresholds = new float[] {1.00f, 0.94f};

        w1 = SolverTest4.hiddenLayers[0].length;
        w2 = SolverTest4.hiddenLayers[0][0].length;
        w3 = w1 * w2;
        w11 = outputLayers.length * outputLayers[0].length;
        hiddenLength = w3 * SolverTest4.hiddenLayers.length;
        allLength = hiddenLength + hiddenThresholds.length + w11 + outputThresholds.length;
    }

    static int w1;
    static int w2 ;
    static int w3;
    static int w11;
    static long hiddenLength;
    static long allLength;

    public static void set(long index, float value) {
        float[][][] hl = SolverTest4.hiddenLayers;
        if (index < hiddenLength) {
            int x = (int) (index / w3);
            int y = (int) ((index - (x * w3)) / w2);
            int z = (int) (index % w2);
            hl[x][y][z] = value;
            return;
        }
        if (index < hiddenLength + hiddenThresholds.length) {
            hiddenThresholds[(int) (index - hiddenLength)] = value;
            return;
        }
        long nextIndex = hiddenLength + hiddenThresholds.length;
        if (index < nextIndex + w11) {
            index = index - nextIndex;
            int x = (int) (index / outputLayers[0].length);
            int y = (int) (index % outputLayers.length);
            SolverTest4.outputLayers[x][y] = value;
            return;
        }
        int lastIndex = (int) (index - nextIndex - w11);
        outputThresholds[lastIndex] = value;
    }
    public static float get(long index) {
        float[][][] hl = SolverTest4.hiddenLayers;
        if (index < hiddenLength) {
            int x = (int) (index / w3);
            int y = (int) ((index - (x * w3)) / w2);
            int z = (int) (index % w2);
            return hl[x][y][z];
        }
        if (index < hiddenLength + hiddenThresholds.length) {
            return hiddenThresholds[(int) (index - hiddenLength)];
        }
        long nextIndex = hiddenLength + hiddenThresholds.length;
        if (index < nextIndex + w11) {
            index = index - nextIndex;
            int x = (int) (index / outputLayers[0].length);
            int y = (int) (index % outputLayers.length);
            return SolverTest4.outputLayers[x][y];
        }
        int lastIndex = (int) (index - nextIndex - w11);
        return outputThresholds[lastIndex];
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
    private static String convertToString(float[][] data) {
        StringBuilder sb = new StringBuilder();
        for (float[] row : data) {
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
        final int Loop = 5000;
        final float Step = 0.1f;
        final float h = 0.0001f;
        SolverTest4.setupData();
        System.out.println(targetFunction());
        for(int b = 0;b < Loop;b++) {
            for (long i = 0;i < allLength;i++) {
                float data = get(i);

                float baseError = targetFunction();
                set(i, data + h);
                float nextError = targetFunction();
                float dxdy = (nextError - baseError) / h;
                data = data - Step * dxdy;
//                if(data < 0) {
//                    data = 0;
//                }
                set(i, data);
//                System.out.println(dxdy);
            }
            System.out.println("b=" + b + " error=" + targetFunction());
            if (targetFunction() < 0.01) {
                break;
            }
        }
        System.out.println("LastError=" + targetFunction());
        System.out.println("hiddenLayers[0]=" + convertToString(SolverTest4.hiddenLayers[0]));
        System.out.println("hiddenLayers[1]=" + convertToString(SolverTest4.hiddenLayers[1]));
        System.out.println("hiddenLayers[2]=" + convertToString(SolverTest4.hiddenLayers[2]));
        System.out.println("hiddenThresholds=" + convertToString(SolverTest4.hiddenThresholds));
        System.out.println("outputLayers=" + convertToString(SolverTest4.outputLayers));
        System.out.println("outputThresholds=" + convertToString(SolverTest4.outputThresholds));
    }
}
