package com.github.horitaku1124.mnist;

public class OutputData {

    /** 畳み込み層 - フィルター */
    public float[][][] convolutionFilters;
    /** 畳み込み層 - θ */
    public float[] convolutionTheta;
    /** 出力層 o1 */
    public float[][][] outputLayer1;
    /** 出力層 o2 */
    public float[][][] outputLayer2;
    /** 出力層 - θ */
    public float[] outputTheta;

    int w1, w2, w3, w4;
    int x1, x2, x3;
    int o11, o12, o13, o14;
    int o21, o22, o23, o24;
    long convolutionLength, output1Length, output2Length;

    long allLength;

    public void set(long index, float value) {
        float[][][] hl = convolutionFilters;
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
    public float get(long index) {
        float[][][] hl = convolutionFilters;
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

    public void initializeData() {
        convolutionFilters = new float[][][] {
                {
                        {1.0f, 1.0f, 1.0f, 1.0f, 1.0f},
                        {1.0f, 1.0f, 1.0f, 1.0f, 1.0f},
                        {1.0f, 1.0f, 1.0f, 1.0f, 1.0f},
                        {1.0f, 1.0f, 1.0f, 1.0f, 1.0f},
                        {1.0f, 1.0f, 1.0f, 1.0f, 1.0f},
                },
                {
                        {0.5f, 0.5f, 0.5f, 0.5f, 0.5f},
                        {0.5f, 0.5f, 0.5f, 0.5f, 0.5f},
                        {0.5f, 0.5f, 0.5f, 0.5f, 0.5f},
                        {0.5f, 0.5f, 0.5f, 0.5f, 0.5f},
                        {0.5f, 0.5f, 0.5f, 0.5f, 0.5f},
                },
                {
                        {0.1f, 0.1f, 0.1f, 0.1f, 0.1f},
                        {0.1f, 0.1f, 0.1f, 0.1f, 0.1f},
                        {0.1f, 0.1f, 0.1f, 0.1f, 0.1f},
                        {0.1f, 0.1f, 0.1f, 0.1f, 0.1f},
                        {0.1f, 0.1f, 0.1f, 0.1f, 0.1f},
                }
        };

        convolutionTheta = new float[]{0, 1.38f, 0.87f};

        outputLayer1 = new float[][][] {
                {
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                },
                {
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                },
                {
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                },

        };

        outputLayer2 = new float[][][] {

                {
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                },
                {
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                },
                {
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
                        {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
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
}
