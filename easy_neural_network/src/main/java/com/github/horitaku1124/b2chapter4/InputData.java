package com.github.horitaku1124.b2chapter4;

import com.github.horitaku1124.util.MyNumArray;

public class InputData {
    /** 入力層 */
    public static MyNumArray inputLayers;
    /** 正解ラベル */
    public static MyNumArray answerLayers;
    static  {
            float[][][] inputLayers = new float[64][3][4];
            inputLayers[0] = new float[][] {
                    {1f,1f,1f},
                    {1f,0f,1f},
                    {1f,0f,1f},
                    {1f,1f,1f},
            };
            inputLayers[1] = new float[][] {
                    {0f,1f,1f},
                    {1f,0f,1f},
                    {1f,0f,1f},
                    {1f,1f,1f},
            };
            inputLayers[2] = new float[][] {
                    {1f,1f,0f},
                    {1f,0f,1f},
                    {1f,0f,1f},
                    {1f,1f,1f},
            };
            inputLayers[3] = new float[][] {
                    {1f,1f,1f},
                    {1f,0f,1f},
                    {1f,0f,1f},
                    {1f,1f,0f},
            };
            inputLayers[4] = new float[][] {
                    {1f,1f,1f},
                    {1f,0f,1f},
                    {1f,0f,1f},
                    {0f,1f,1f},
            };
            inputLayers[5] = new float[][] {
                    {0f,0f,0f},
                    {1f,1f,1f},
                    {1f,0f,1f},
                    {1f,1f,1f},
            };
            inputLayers[6] = new float[][] {
                    {0f,0f,0f},
                    {0f,1f,1f},
                    {1f,0f,1f},
                    {1f,1f,1f},
            };
            inputLayers[7] = new float[][] {
                    {0f,0f,0f},
                    {1f,1f,0f},
                    {1f,0f,1f},
                    {1f,1f,1f},
            };
            inputLayers[8] = new float[][] {
                    {0f,0f,0f},
                    {1f,1f,1f},
                    {1f,0f,1f},
                    {1f,1f,0f},
            };
            inputLayers[9] = new float[][] {
                    {0f,0f,0f},
                    {1f,1f,1f},
                    {1f,0f,1f},
                    {0f,1f,1f},
            };
            inputLayers[10] = new float[][] {
                    {1f,1f,1f},
                    {1f,0f,1f},
                    {1f,1f,1f},
                    {0f,0f,0f},
            };
            inputLayers[11] = new float[][] {
                    {0f,1f,1f},
                    {1f,0f,1f},
                    {1f,1f,1f},
                    {0f,0f,0f},
            };
            inputLayers[12] = new float[][] {
                    {1f,1f,0f},
                    {1f,0f,1f},
                    {1f,1f,1f},
                    {0f,0f,0f},
            };
            inputLayers[13] = new float[][] {
                    {1f,1f,1f},
                    {1f,0f,1f},
                    {1f,1f,0f},
                    {0f,0f,0f},
            };
            inputLayers[14] = new float[][] {
                    {1f,1f,1f},
                    {1f,0f,1f},
                    {0f,1f,1f},
                    {0f,0f,0f},
            };
            inputLayers[15] = new float[][] {
                    {1f,0f,1f},
                    {1f,0f,1f},
                    {1f,0f,1f},
                    {1f,1f,1f},
            };
            inputLayers[16] = new float[][] {
                    {1f,1f,1f},
                    {1f,0f,0f},
                    {1f,0f,1f},
                    {1f,1f,1f},
            };
            inputLayers[17] = new float[][] {
                    {1f,1f,1f},
                    {1f,0f,1f},
                    {1f,0f,0f},
                    {1f,1f,1f},
            };
            inputLayers[18] = new float[][] {
                    {1f,1f,1f},
                    {1f,0f,1f},
                    {1f,0f,1f},
                    {1f,0f,1f},
            };
            inputLayers[19] = new float[][] {
                    {1f,1f,1f},
                    {1f,0f,1f},
                    {0f,0f,1f},
                    {1f,1f,1f},
            };
            inputLayers[20] = new float[][] {
                    {1f,1f,1f},
                    {0f,0f,1f},
                    {1f,0f,1f},
                    {1f,1f,1f},
            };
            inputLayers[21] = new float[][] {
                    {0f,0f,1f},
                    {1f,0f,1f},
                    {1f,0f,1f},
                    {1f,1f,1f},
            };
            inputLayers[22] = new float[][] {
                    {0f,1f,1f},
                    {1f,0f,0f},
                    {1f,0f,1f},
                    {1f,1f,1f},
            };
            inputLayers[23] = new float[][] {
                    {0f,1f,1f},
                    {1f,0f,1f},
                    {1f,0f,0f},
                    {1f,1f,1f},
            };
            inputLayers[24] = new float[][] {
                    {0f,1f,1f},
                    {1f,0f,1f},
                    {1f,0f,1f},
                    {1f,0f,1f},
            };
            inputLayers[25] = new float[][] {
                    {0f,1f,1f},
                    {1f,0f,1f},
                    {0f,0f,1f},
                    {1f,1f,1f},
            };
            inputLayers[26] = new float[][] {
                    {0f,1f,1f},
                    {0f,0f,1f},
                    {1f,0f,1f},
                    {1f,1f,1f},
            };
            inputLayers[27] = new float[][] {
                    {1f,1f,0f},
                    {1f,0f,0f},
                    {1f,0f,1f},
                    {1f,1f,1f},
            };
            inputLayers[28] = new float[][] {
                    {1f,1f,0f},
                    {1f,0f,1f},
                    {1f,0f,0f},
                    {1f,1f,1f},
            };
            inputLayers[29] = new float[][] {
                    {1f,1f,0f},
                    {1f,0f,1f},
                    {1f,0f,1f},
                    {1f,0f,1f},
            };
            inputLayers[30] = new float[][] {
                    {1f,1f,0f},
                    {1f,0f,1f},
                    {0f,0f,1f},
                    {1f,1f,1f},
            };
            inputLayers[31] = new float[][] {
                    {1f,1f,0f},
                    {0f,0f,1f},
                    {1f,0f,1f},
                    {1f,1f,1f},
            };
            inputLayers[32] = new float[][] {
                    {0f,1f,0f},
                    {0f,1f,0f},
                    {0f,1f,0f},
                    {0f,1f,0f},
            };
            inputLayers[33] = new float[][] {
                    {1f,1f,0f},
                    {0f,1f,0f},
                    {0f,1f,0f},
                    {0f,1f,0f},
            };
            inputLayers[34] = new float[][] {
                    {0f,1f,0f},
                    {0f,1f,0f},
                    {0f,1f,0f},
                    {0f,1f,0f},
            };
            inputLayers[35] = new float[][] {
                    {0f,1f,0f},
                    {0f,1f,0f},
                    {0f,1f,0f},
                    {1f,1f,0f},
            };
            inputLayers[36] = new float[][] {
                    {0f,1f,0f},
                    {0f,1f,0f},
                    {0f,1f,0f},
                    {0f,1f,1f},
            };
            inputLayers[37] = new float[][] {
                    {1f,1f,0f},
                    {0f,1f,0f},
                    {0f,1f,0f},
                    {1f,1f,0f},
            };
            inputLayers[38] = new float[][] {
                    {1f,1f,0f},
                    {0f,1f,0f},
                    {0f,1f,0f},
                    {0f,1f,1f},
            };
            inputLayers[39] = new float[][] {
                    {1f,1f,0f},
                    {0f,1f,0f},
                    {0f,1f,0f},
                    {1f,1f,1f},
            };
            inputLayers[40] = new float[][] {
                    {0f,1f,0f},
                    {0f,1f,1f},
                    {0f,1f,0f},
                    {0f,1f,0f},
            };
            inputLayers[41] = new float[][] {
                    {0f,1f,0f},
                    {0f,1f,0f},
                    {0f,1f,1f},
                    {0f,1f,0f},
            };
            inputLayers[42] = new float[][] {
                    {1f,1f,0f},
                    {0f,1f,1f},
                    {0f,1f,0f},
                    {0f,1f,0f},
            };
            inputLayers[43] = new float[][] {
                    {1f,1f,0f},
                    {0f,1f,0f},
                    {0f,1f,1f},
                    {0f,1f,0f},
            };
            inputLayers[44] = new float[][] {
                    {0f,1f,0f},
                    {0f,1f,1f},
                    {0f,1f,0f},
                    {1f,1f,0f},
            };
            inputLayers[45] = new float[][] {
                    {0f,1f,0f},
                    {0f,1f,0f},
                    {0f,1f,1f},
                    {1f,1f,0f},
            };
            inputLayers[46] = new float[][] {
                    {0f,1f,0f},
                    {0f,1f,0f},
                    {0f,1f,0f},
                    {1f,1f,1f},
            };
            inputLayers[47] = new float[][] {
                    {1f,1f,0f},
                    {0f,1f,1f},
                    {0f,1f,1f},
                    {0f,1f,1f},
            };
            inputLayers[48] = new float[][] {
                    {1f,1f,0f},
                    {0f,1f,0f},
                    {0f,1f,0f},
                    {0f,1f,0f},
            };
            inputLayers[49] = new float[][] {
                    {0f,1f,1f},
                    {0f,1f,1f},
                    {0f,1f,1f},
                    {0f,1f,1f},
            };
            inputLayers[50] = new float[][] {
                    {1f,1f,0f},
                    {1f,1f,0f},
                    {0f,1f,0f},
                    {0f,1f,0f},
            };
            inputLayers[51] = new float[][] {
                    {1f,1f,0f},
                    {0f,1f,0f},
                    {1f,1f,0f},
                    {0f,1f,0f},
            };
            inputLayers[52] = new float[][] {
                    {1f,1f,0f},
                    {1f,1f,0f},
                    {1f,1f,0f},
                    {1f,1f,0f},
            };
            inputLayers[53] = new float[][] {
                    {1f,1f,0f},
                    {0f,1f,0f},
                    {0f,0f,0f},
                    {0f,1f,0f},
            };
            inputLayers[54] = new float[][] {
                    {0f,1f,0f},
                    {0f,1f,0f},
                    {0f,1f,0f},
                    {1f,0f,0f},
            };
            inputLayers[55] = new float[][] {
                    {1f,0f,0f},
                    {0f,1f,0f},
                    {0f,1f,0f},
                    {0f,1f,0f},
            };
            inputLayers[56] = new float[][] {
                    {1f,0f,0f},
                    {0f,1f,0f},
                    {0f,1f,0f},
                    {0f,0f,1f},
            };
            inputLayers[57] = new float[][] {
                    {0f,1f,0f},
                    {0f,0f,0f},
                    {0f,1f,0f},
                    {1f,1f,0f},
            };
            inputLayers[58] = new float[][] {
                    {0f,1f,0f},
                    {0f,1f,0f},
                    {0f,0f,0f},
                    {1f,1f,0f},
            };
            inputLayers[59] = new float[][] {
                    {0f,0f,0f},
                    {0f,1f,0f},
                    {0f,1f,0f},
                    {1f,1f,0f},
            };
            inputLayers[60] = new float[][] {
                    {0f,0f,0f},
                    {0f,1f,0f},
                    {0f,1f,0f},
                    {0f,1f,0f},
            };
            inputLayers[61] = new float[][] {
                    {0f,1f,0f},
                    {0f,1f,0f},
                    {0f,1f,0f},
                    {0f,0f,0f},
            };
            inputLayers[62] = new float[][] {
                    {0f,1f,0f},
                    {0f,0f,1f},
                    {0f,0f,1f},
                    {0f,1f,0f},
            };
            inputLayers[63] = new float[][] {
                    {0f,1f,0f},
                    {1f,0f,0f},
                    {1f,0f,0f},
                    {0f,1f,0f},
            };
            InputData.inputLayers = new MyNumArray(inputLayers);
            answerLayers = new MyNumArray(new float[][]{
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
                {0,1},
            });
    }
}
