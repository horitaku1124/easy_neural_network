package com.github.horitaku1124.chapter5;

import com.github.horitaku1124.util.MyNumArray;

import java.util.Arrays;
import java.util.List;

public class OutputData2 {

    /** 畳み込み層 - フィルター */
    public MyNumArray convolutionFilters;
    /** 畳み込み層 - θ */
    public MyNumArray convolutionTheta;
    /** 出力層 o1 */
    public MyNumArray outputLayer1;
    /** 出力層 o2 */
    public MyNumArray outputLayer2;
    /** 出力層 - θ */
    public MyNumArray outputTheta;

    private List<MyNumArray> allData;

    long allLength;

    public void set(long index, float value) {
        long offset = index;
        for (int i = 0;i < allData.size();i++) {
            if (offset < allData.get(i).size()) {
                allData.get(i).internalData[(int) offset] = value;
                return;
            }
            offset -= allData.get(i).size;
        }
        throw new ArrayIndexOutOfBoundsException((int) index);
    }
    public float get(long index) {
        long offset = index;
        for (int i = 0;i < allData.size();i++) {
            if (offset < allData.get(i).size()) {
                return allData.get(i).internalData[(int) offset];
            }
            offset -= allData.get(i).size;
        }
        throw new ArrayIndexOutOfBoundsException((int) index);
    }

    public void initializeData() {
        float[][][] convolutionFilters = new float[][][] {
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
        this.convolutionFilters = new MyNumArray(convolutionFilters);


        float[] convolutionTheta = new float[]{0, 1.38f, 0.87f};
        this.convolutionTheta = new MyNumArray(convolutionTheta);

        float[][][] outputLayer1 = new float[][][] {
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
        this.outputLayer1 = new MyNumArray(outputLayer1);

        float[][][] outputLayer2 = new float[][][] {

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
        this.outputLayer2 = new MyNumArray(outputLayer2);

        float[] outputTheta = new float[]{2.48f, 2.27f};
        this.outputTheta = new MyNumArray(outputTheta);

        allData = Arrays.asList(this.convolutionFilters, this.convolutionTheta, this.outputLayer1, this.outputLayer2, this.outputTheta);

        allLength = allData.stream().mapToLong(MyNumArray::size).sum();
    }
}
