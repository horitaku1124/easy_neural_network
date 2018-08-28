package com.github.horitaku1124.chapter4;

public class SolverTest4 {
    /** 入力層 */
    private static int[][][] inputLayers;
    /** 正解ラベル */
    private static float[][] layerAnswers;
    /** 隠れ層 */
    private static float[][][] hiddenLayers;
    /** 隠れ層-閾値 */
    private static float[] hiddenThresholds;
    /** 出力層 */
    private static float[][] outputLayers;
    /** 出力層-域値 */
    private static float[] outputThresholds;
    public static void setupData() {
        int[][][] inputLayers = new int[64][3][3];
        float[][] layerAnswers = new float[64][2];
        inputLayers[0] = new int[][]{
                {1,1,1},
                {1,0,1},
                {1,0,1},
                {1,1,1},
        };
        layerAnswers[0] = new float[]{1,0};
        inputLayers[1] = new int[][]{
                {0,1,1},
                {1,0,1},
                {1,0,1},
                {1,1,1},
        };
        layerAnswers[1] = new float[]{1,0};
        inputLayers[2] = new int[][]{
                {1,1,0},
                {1,0,1},
                {1,0,1},
                {1,1,1},
        };
        layerAnswers[2] = new float[]{1,0};
        inputLayers[3] = new int[][]{
                {1,1,1},
                {1,0,1},
                {1,0,1},
                {1,1,0},
        };
        layerAnswers[3] = new float[]{1,0};
        inputLayers[4] = new int[][]{
                {1,1,1},
                {1,0,1},
                {1,0,1},
                {0,1,1},
        };
        layerAnswers[4] = new float[]{1,0};
        inputLayers[5] = new int[][]{
                {0,0,0},
                {1,1,1},
                {1,0,1},
                {1,1,1},
        };
        layerAnswers[5] = new float[]{1,0};
        inputLayers[6] = new int[][]{
                {0,0,0},
                {0,1,1},
                {1,0,1},
                {1,1,1},
        };
        layerAnswers[6] = new float[]{1,0};
        inputLayers[7] = new int[][]{
                {0,0,0},
                {1,1,0},
                {1,0,1},
                {1,1,1},
        };
        layerAnswers[7] = new float[]{1,0};
        inputLayers[8] = new int[][]{
                {0,0,0},
                {1,1,1},
                {1,0,1},
                {1,1,0},
        };
        layerAnswers[8] = new float[]{1,0};
        inputLayers[9] = new int[][]{
                {0,0,0},
                {1,1,1},
                {1,0,1},
                {0,1,1},
        };
        layerAnswers[9] = new float[]{1,0};
        inputLayers[10] = new int[][]{
                {1,1,1},
                {1,0,1},
                {1,1,1},
                {0,0,0},
        };
        layerAnswers[10] = new float[]{1,0};
        inputLayers[11] = new int[][]{
                {0,1,1},
                {1,0,1},
                {1,1,1},
                {0,0,0},
        };
        layerAnswers[11] = new float[]{1,0};
        inputLayers[12] = new int[][]{
                {1,1,0},
                {1,0,1},
                {1,1,1},
                {0,0,0},
        };
        layerAnswers[12] = new float[]{1,0};
        inputLayers[13] = new int[][]{
                {1,1,1},
                {1,0,1},
                {1,1,0},
                {0,0,0},
        };
        layerAnswers[13] = new float[]{1,0};
        inputLayers[14] = new int[][]{
                {1,1,1},
                {1,0,1},
                {0,1,1},
                {0,0,0},
        };
        layerAnswers[14] = new float[]{1,0};
        inputLayers[15] = new int[][]{
                {1,0,1},
                {1,0,1},
                {1,0,1},
                {1,1,1},
        };
        layerAnswers[15] = new float[]{1,0};
        inputLayers[16] = new int[][]{
                {1,1,1},
                {1,0,0},
                {1,0,1},
                {1,1,1},
        };
        layerAnswers[16] = new float[]{1,0};
        inputLayers[17] = new int[][]{
                {1,1,1},
                {1,0,1},
                {1,0,0},
                {1,1,1},
        };
        layerAnswers[17] = new float[]{1,0};
        inputLayers[18] = new int[][]{
                {1,1,1},
                {1,0,1},
                {1,0,1},
                {1,0,1},
        };
        layerAnswers[18] = new float[]{1,0};
        inputLayers[19] = new int[][]{
                {1,1,1},
                {1,0,1},
                {0,0,1},
                {1,1,1},
        };
        layerAnswers[19] = new float[]{1,0};
        inputLayers[20] = new int[][]{
                {1,1,1},
                {0,0,1},
                {1,0,1},
                {1,1,1},
        };
        layerAnswers[20] = new float[]{1,0};
        inputLayers[21] = new int[][]{
                {0,0,1},
                {1,0,1},
                {1,0,1},
                {1,1,1},
        };
        layerAnswers[21] = new float[]{1,0};
        inputLayers[22] = new int[][]{
                {0,1,1},
                {1,0,0},
                {1,0,1},
                {1,1,1},
        };
        layerAnswers[22] = new float[]{1,0};
        inputLayers[23] = new int[][]{
                {0,1,1},
                {1,0,1},
                {1,0,0},
                {1,1,1},
        };
        layerAnswers[23] = new float[]{1,0};
        inputLayers[24] = new int[][]{
                {0,1,1},
                {1,0,1},
                {1,0,1},
                {1,0,1},
        };
        layerAnswers[24] = new float[]{1,0};
        inputLayers[25] = new int[][]{
                {0,1,1},
                {1,0,1},
                {0,0,1},
                {1,1,1},
        };
        layerAnswers[25] = new float[]{1,0};
        inputLayers[26] = new int[][]{
                {0,1,1},
                {0,0,1},
                {1,0,1},
                {1,1,1},
        };
        layerAnswers[26] = new float[]{1,0};
        inputLayers[27] = new int[][]{
                {1,1,0},
                {1,0,0},
                {1,0,1},
                {1,1,1},
        };
        layerAnswers[27] = new float[]{1,0};
        inputLayers[28] = new int[][]{
                {1,1,0},
                {1,0,1},
                {1,0,0},
                {1,1,1},
        };
        layerAnswers[28] = new float[]{1,0};
        inputLayers[29] = new int[][]{
                {1,1,0},
                {1,0,1},
                {1,0,1},
                {1,0,1},
        };
        layerAnswers[29] = new float[]{1,0};
        inputLayers[30] = new int[][]{
                {1,1,0},
                {1,0,1},
                {0,0,1},
                {1,1,1},
        };
        layerAnswers[30] = new float[]{1,0};
        inputLayers[31] = new int[][]{
                {1,1,0},
                {0,0,1},
                {1,0,1},
                {1,1,1},
        };
        layerAnswers[31] = new float[]{1,0};
        inputLayers[32] = new int[][]{
                {1,0,1},
                {0,1,0},
                {1,0,1},
                {0,0,0},
        };
        layerAnswers[32] = new float[]{0,1};
        inputLayers[33] = new int[][]{
                {1,0,1},
                {0,1,0},
                {1,0,1},
                {1,0,0},
        };
        layerAnswers[33] = new float[]{0,1};
        inputLayers[34] = new int[][]{
                {1,0,1},
                {0,1,0},
                {1,0,1},
                {0,0,1},
        };
        layerAnswers[34] = new float[]{0,1};
        inputLayers[35] = new int[][]{
                {1,0,1},
                {0,1,0},
                {0,1,0},
                {1,0,1},
        };
        layerAnswers[35] = new float[]{0,1};
        inputLayers[36] = new int[][]{
                {1,0,1},
                {0,1,0},
                {1,1,0},
                {1,0,1},
        };
        layerAnswers[36] = new float[]{0,1};
        inputLayers[37] = new int[][]{
                {1,0,1},
                {0,1,0},
                {0,1,1},
                {1,0,1},
        };
        layerAnswers[37] = new float[]{0,1};
        inputLayers[38] = new int[][]{
                {1,0,1},
                {0,1,0},
                {1,1,1},
                {1,0,1},
        };
        layerAnswers[38] = new float[]{0,1};
        inputLayers[39] = new int[][]{
                {1,0,1},
                {1,1,0},
                {0,1,0},
                {1,0,1},
        };
        layerAnswers[39] = new float[]{0,1};
        inputLayers[40] = new int[][]{
                {1,0,1},
                {0,1,1},
                {0,1,0},
                {1,0,1},
        };
        layerAnswers[40] = new float[]{0,1};
        inputLayers[41] = new int[][]{
                {1,0,1},
                {1,1,1},
                {0,1,0},
                {1,0,1},
        };
        layerAnswers[41] = new float[]{0,1};
        inputLayers[42] = new int[][]{
                {0,0,0},
                {1,0,1},
                {0,1,0},
                {1,0,1},
        };
        layerAnswers[42] = new float[]{0,1};
        inputLayers[43] = new int[][]{
                {1,0,0},
                {1,0,1},
                {0,1,0},
                {1,0,1},
        };
        layerAnswers[43] = new float[]{0,1};
        inputLayers[44] = new int[][]{
                {0,0,1},
                {1,0,1},
                {0,1,0},
                {1,0,1},
        };
        layerAnswers[44] = new float[]{0,1};
        inputLayers[45] = new int[][]{
                {1,0,1},
                {1,1,0},
                {0,1,1},
                {1,0,1},
        };
        layerAnswers[45] = new float[]{0,1};
        inputLayers[46] = new int[][]{
                {1,0,1},
                {0,1,0},
                {1,1,0},
                {1,0,1},
        };
        layerAnswers[46] = new float[]{0,1};
        inputLayers[47] = new int[][]{
                {1,0,1},
                {0,1,1},
                {1,1,0},
                {1,0,1},
        };
        layerAnswers[47] = new float[]{0,1};
        inputLayers[48] = new int[][]{
                {1,0,1},
                {0,1,0},
                {0,0,0},
                {1,0,1},
        };
        layerAnswers[48] = new float[]{0,1};
        inputLayers[49] = new int[][]{
                {1,0,1},
                {0,1,1},
                {0,0,0},
                {1,0,1},
        };
        layerAnswers[49] = new float[]{0,1};
        inputLayers[50] = new int[][]{
                {1,0,1},
                {1,1,0},
                {0,0,0},
                {1,0,1},
        };
        layerAnswers[50] = new float[]{0,1};
        inputLayers[51] = new int[][]{
                {1,0,1},
                {0,0,0},
                {0,1,0},
                {1,0,1},
        };
        layerAnswers[51] = new float[]{0,1};
        inputLayers[52] = new int[][]{
                {1,0,1},
                {0,0,0},
                {1,1,0},
                {1,0,1},
        };
        layerAnswers[52] = new float[]{0,1};
        inputLayers[53] = new int[][]{
                {1,0,1},
                {0,0,0},
                {0,1,1},
                {1,0,1},
        };
        layerAnswers[53] = new float[]{0,1};
        inputLayers[54] = new int[][]{
                {1,0,1},
                {0,1,0},
                {0,1,1},
                {1,0,0},
        };
        layerAnswers[54] = new float[]{0,1};
        inputLayers[55] = new int[][]{
                {1,0,1},
                {0,1,0},
                {1,1,0},
                {0,0,1},
        };
        layerAnswers[55] = new float[]{0,1};
        inputLayers[56] = new int[][]{
                {1,0,0},
                {0,1,1},
                {0,1,0},
                {1,0,1},
        };
        layerAnswers[56] = new float[]{0,1};
        inputLayers[57] = new int[][]{
                {0,0,1},
                {1,1,0},
                {0,1,0},
                {1,0,1},
        };
        layerAnswers[57] = new float[]{0,1};
        inputLayers[58] = new int[][]{
                {1,0,1},
                {0,1,0},
                {0,1,0},
                {1,0,0},
        };
        layerAnswers[58] = new float[]{0,1};
        inputLayers[59] = new int[][]{
                {1,0,1},
                {0,1,0},
                {0,1,0},
                {0,0,1},
        };
        layerAnswers[59] = new float[]{0,1};
        inputLayers[60] = new int[][]{
                {1,0,0},
                {0,1,0},
                {0,1,0},
                {1,0,1},
        };
        layerAnswers[60] = new float[]{0,1};
        inputLayers[61] = new int[][]{
                {0,0,1},
                {0,1,0},
                {0,1,0},
                {1,0,1},
        };
        layerAnswers[61] = new float[]{0,1};
        inputLayers[62] = new int[][]{
                {1,0,0},
                {1,0,0},
                {0,1,0},
                {1,0,1},
        };
        layerAnswers[62] = new float[]{0,1};
        inputLayers[63] = new int[][]{
                {0,0,1},
                {1,0,1},
                {0,1,0},
                {1,0,0},
        };
        layerAnswers[63] = new float[]{0,1};


        SolverTest4.inputLayers = inputLayers;
        SolverTest4.layerAnswers = layerAnswers;

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

    private static float sumProduct(float[] inputLayers, float[] hiddenLayers) {
        float sum = 0;
        int x = inputLayers.length;
        for(int i = 0;i < x;i++) {
            sum += inputLayers[i] * hiddenLayers[i];
        }
        return sum;
    }
    private static float sumProduct(int[][] inputLayers, float[][] hiddenLayers) {
        float sum = 0;
        int x = inputLayers.length;
        int y = inputLayers[0].length;
        for(int i = 0;i < x;i++) {
            for (int j = 0;j < y;j++) {
                sum += inputLayers[i][j] * hiddenLayers[i][j];
            }
        }
        return sum;
    }
    private static float calculateOutputY(int[][] inputLayers, float[][] hiddenLayers, float threshold) {
        return 1.0f / (1.0f + (float)Math.exp(
                (-sumProduct(inputLayers, hiddenLayers) + threshold)
        ));
    }
    private static float calculateOutputZ(float[] outputY, float[] outputLayers, float outputThreshold) {
        return 1.0f / (1.0f + (float)Math.exp(
                (-sumProduct(outputY, outputLayers) + outputThreshold)
        ));
    }
    private static float SUMXMY2(float[] correct, float[] answer) {
        int x = correct.length;
        float sum = 0;
        for (int i = 0;i < x;i++) {
            sum += Math.pow(correct[i] - answer[i], 2);
        }
        return sum;
    }

    public static void main(String[] args) {
        SolverTest4.setupData();

        float[][] outputYResult = new float[64][3];
        float error = 0;
        for (int i = 0; i < SolverTest4.inputLayers.length; i++) {
            for (int j = 0;j < 3;j++) {
                float output = calculateOutputY(
                        SolverTest4.inputLayers[i], SolverTest4.hiddenLayers[j], SolverTest4.hiddenThresholds[j]);
                outputYResult[i][j] = output;
            }
            float[] output1 = new float[]{
                calculateOutputZ(outputYResult[i], SolverTest4.outputLayers[0], SolverTest4.outputThresholds[0]),
                calculateOutputZ(outputYResult[i], SolverTest4.outputLayers[1], SolverTest4.outputThresholds[1])
            };
            error += SUMXMY2(SolverTest4.layerAnswers[i], output1);
        }
        System.out.println(error);
    }
}
