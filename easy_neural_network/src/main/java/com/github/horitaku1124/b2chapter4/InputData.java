package com.github.horitaku1124.b2chapter4;

import com.github.horitaku1124.util.MyNumArray;

public class InputData {
    /** 入力層 */
    public static MyNumArray inputLayers;
    /** 正解ラベル */
    public static MyNumArray answerLayers;
    static  {
        float[][][] inputLayers = {
                {
                        {1,1,1},
                        {1,0,1},
                        {1,0,1},
                        {1,1,1},
                },
                {
                        {0,1,1},
                        {1,0,1},
                        {1,0,1},
                        {1,1,1},
                },
                {
                        {1,1,0},
                        {1,0,1},
                        {1,0,1},
                        {1,1,1},
                },
                {
                        {1,1,1},
                        {1,0,1},
                        {1,0,1},
                        {1,1,0},
                },
                {
                        {1,1,1},
                        {1,0,1},
                        {1,0,1},
                        {0,1,1},
                },
                {
                        {0,0,0},
                        {1,1,1},
                        {1,0,1},
                        {1,1,1},
                },
                {
                        {0,0,0},
                        {0,1,1},
                        {1,0,1},
                        {1,1,1},
                },
                {
                        {0,0,0},
                        {1,1,0},
                        {1,0,1},
                        {1,1,1},
                },
                {
                        {0,0,0},
                        {1,1,1},
                        {1,0,1},
                        {1,1,0},
                },
                {
                        {0,0,0},
                        {1,1,1},
                        {1,0,1},
                        {0,1,1},
                },
                {
                        {1,1,1},
                        {1,0,1},
                        {1,1,1},
                        {0,0,0},
                },
                {
                        {0,1,1},
                        {1,0,1},
                        {1,1,1},
                        {0,0,0},
                },
                {
                        {1,1,0},
                        {1,0,1},
                        {1,1,1},
                        {0,0,0},
                },
                {
                        {1,1,1},
                        {1,0,1},
                        {1,1,0},
                        {0,0,0},
                },
                {
                        {1,1,1},
                        {1,0,1},
                        {0,1,1},
                        {0,0,0},
                },
                {
                        {1,0,1},
                        {1,0,1},
                        {1,0,1},
                        {1,1,1},
                },
                {
                        {1,1,1},
                        {1,0,0},
                        {1,0,1},
                        {1,1,1},
                },
                {
                        {1,1,1},
                        {1,0,1},
                        {1,0,0},
                        {1,1,1},
                },
                {
                        {1,1,1},
                        {1,0,1},
                        {1,0,1},
                        {1,0,1},
                },
                {
                        {1,1,1},
                        {1,0,1},
                        {0,0,1},
                        {1,1,1},
                },
                {
                        {1,1,1},
                        {0,0,1},
                        {1,0,1},
                        {1,1,1},
                },
                {
                        {0,0,1},
                        {1,0,1},
                        {1,0,1},
                        {1,1,1},
                },
                {
                        {0,1,1},
                        {1,0,0},
                        {1,0,1},
                        {1,1,1},
                },
                {
                        {0,1,1},
                        {1,0,1},
                        {1,0,0},
                        {1,1,1},
                },
                {
                        {0,1,1},
                        {1,0,1},
                        {1,0,1},
                        {1,0,1},
                },
                {
                        {0,1,1},
                        {1,0,1},
                        {0,0,1},
                        {1,1,1},
                },
                {
                        {0,1,1},
                        {0,0,1},
                        {1,0,1},
                        {1,1,1},
                },
                {
                        {1,1,0},
                        {1,0,0},
                        {1,0,1},
                        {1,1,1},
                },
                {
                        {1,1,0},
                        {1,0,1},
                        {1,0,0},
                        {1,1,1},
                },
                {
                        {1,1,0},
                        {1,0,1},
                        {1,0,1},
                        {1,0,1},
                },
                {
                        {1,1,0},
                        {1,0,1},
                        {0,0,1},
                        {1,1,1},
                },
                {
                        {1,1,0},
                        {0,0,1},
                        {1,0,1},
                        {1,1,1},
                },
                {
                        {1,0,1},
                        {0,1,0},
                        {1,0,1},
                        {0,0,0},
                },
                {
                        {1,0,1},
                        {0,1,0},
                        {1,0,1},
                        {1,0,0},
                },
                {
                        {1,0,1},
                        {0,1,0},
                        {1,0,1},
                        {0,0,1},
                },
                {
                        {1,0,1},
                        {0,1,0},
                        {0,1,0},
                        {1,0,1},
                },
                {
                        {1,0,1},
                        {0,1,0},
                        {1,1,0},
                        {1,0,1},
                },
                {
                        {1,0,1},
                        {0,1,0},
                        {0,1,1},
                        {1,0,1},
                },
                {
                        {1,0,1},
                        {0,1,0},
                        {1,1,1},
                        {1,0,1},
                },
                {
                        {1,0,1},
                        {1,1,0},
                        {0,1,0},
                        {1,0,1},
                },
                {
                        {1,0,1},
                        {0,1,1},
                        {0,1,0},
                        {1,0,1},
                },
                {
                        {1,0,1},
                        {1,1,1},
                        {0,1,0},
                        {1,0,1},
                },
                {
                        {0,0,0},
                        {1,0,1},
                        {0,1,0},
                        {1,0,1},
                },
                {
                        {1,0,0},
                        {1,0,1},
                        {0,1,0},
                        {1,0,1},
                },
                {
                        {0,0,1},
                        {1,0,1},
                        {0,1,0},
                        {1,0,1},
                },
                {
                        {1,0,1},
                        {1,1,0},
                        {0,1,1},
                        {1,0,1},
                },
                {
                        {1,0,1},
                        {0,1,0},
                        {1,1,0},
                        {1,0,1},
                },
                {
                        {1,0,1},
                        {0,1,1},
                        {1,1,0},
                        {1,0,1},
                },
                {
                        {1,0,1},
                        {0,1,0},
                        {0,0,0},
                        {1,0,1},
                },
                {
                        {1,0,1},
                        {0,1,1},
                        {0,0,0},
                        {1,0,1},
                },
                {
                        {1,0,1},
                        {1,1,0},
                        {0,0,0},
                        {1,0,1},
                },
                {
                        {1,0,1},
                        {0,0,0},
                        {0,1,0},
                        {1,0,1},
                },
                {
                        {1,0,1},
                        {0,0,0},
                        {1,1,0},
                        {1,0,1},
                },
                {
                        {1,0,1},
                        {0,0,0},
                        {0,1,1},
                        {1,0,1},
                },
                {
                        {1,0,1},
                        {0,1,0},
                        {0,1,1},
                        {1,0,0},
                },
                {
                        {1,0,1},
                        {0,1,0},
                        {1,1,0},
                        {0,0,1},
                },
                {
                        {1,0,0},
                        {0,1,1},
                        {0,1,0},
                        {1,0,1},
                },
                {
                        {0,0,1},
                        {1,1,0},
                        {0,1,0},
                        {1,0,1},
                },
                {
                        {1,0,1},
                        {0,1,0},
                        {0,1,0},
                        {1,0,0},
                },
                {
                        {1,0,1},
                        {0,1,0},
                        {0,1,0},
                        {0,0,1},
                },
                {
                        {1,0,0},
                        {0,1,0},
                        {0,1,0},
                        {1,0,1},
                },
                {
                        {0,0,1},
                        {0,1,0},
                        {0,1,0},
                        {1,0,1},
                },
                {
                        {1,0,0},
                        {1,0,0},
                        {0,1,0},
                        {1,0,1},
                },
                {
                        {0,0,1},
                        {1,0,1},
                        {0,1,0},
                        {1,0,0},
                },
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
