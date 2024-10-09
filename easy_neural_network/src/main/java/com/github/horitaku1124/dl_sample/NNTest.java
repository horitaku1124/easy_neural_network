package com.github.horitaku1124.dl_sample;

import com.github.horitaku1124.dl_sample.layer.LayerBase;
import com.github.horitaku1124.dl_sample.layer.MiddleLayer;
import com.github.horitaku1124.dl_sample.layer.OutputLayer;
import com.github.horitaku1124.util.ArrayWarp;
import com.github.horitaku1124.util.CSVReader;
import com.github.horitaku1124.util.MyNumArray;
import com.github.horitaku1124.util.WarpMyNumArray;

import java.io.IOException;

public class NNTest {
    static MiddleLayer middleLayer1;
    static MiddleLayer middleLayer2;
    static OutputLayer outputLayer;

    private static void forwardPropagation(MyNumArray x) {
        middleLayer1.forward(x);
        middleLayer2.forward(middleLayer1.y);
        outputLayer.forward(middleLayer2.y);
    }

    private static void backPropagation(MyNumArray t) {
        outputLayer.backward(t);;
        middleLayer2.backward(outputLayer.grad_x);
        middleLayer1.backward(middleLayer2.grad_x);
    }

    private static void updateWb(float eta) {
        middleLayer1.update(eta);
        middleLayer2.update(eta);
        outputLayer.update(eta);
    }
//    private static float getError(MyNumArray t, int batch_size) {
//        return MyNumArray.sum(t * )
//    }

    public static void main(String[] args) throws IOException {
        MyNumArray irisImageData;
        MyNumArray irisLabelData;
        try(CSVReader csvReader = new CSVReader("./src/main/resources/iris.csv", 1);) {
            irisImageData = csvReader.readToMuyNum(s -> {
                String[] line = s.split(",");
                return new Float[]{
                        Float.parseFloat(line[0]),
                        Float.parseFloat(line[1]),
                        Float.parseFloat(line[2]),
                        Float.parseFloat(line[3]),
                };
            });
            irisImageData.printLayer(0);
        }
        try(CSVReader csvReader = new CSVReader("./src/main/resources/iris.csv", 1);) {
            irisLabelData = csvReader.readToMuyNum(s -> {
                String[] line = s.split(",");
                Float[] correct = new Float[3];
                int index = (int)Float.parseFloat(line[5]);
                for (int i = 0;i < 3;i++) {
                    correct[i] = (float) 0;
                }
                correct[index] = 1f;
                return correct;
            });
        }
        int dataNum = irisImageData.layerLength(0);
        MyNumArray average = MyNumArray.average(irisImageData, 0);
        MyNumArray std = MyNumArray.std(irisImageData, 0);
        irisImageData = irisImageData.broadcastSub(average).broadcastDivide(std);
        int[] index_train = MyNumArray.arange(dataNum, i -> i % 2 == 0);
        int[] index_test = MyNumArray.arange(dataNum, i -> i % 2 != 0);

        MyNumArray inputTrain = irisImageData.getIn(index_train);
        MyNumArray correctTrain = irisLabelData.getIn(index_train);

        MyNumArray inputTest = irisImageData.getIn(index_test);
        MyNumArray correctTest = irisLabelData.getIn(index_test);

        int n_train = inputTrain.layerLength(0); // 訓練データに使う数
        int n_test = inputTest.layerLength(0); // テストデータに数

        int n_in = 4;
        int n_mid = 25;
        int n_out = 3;

        float wb_width = 0.1f;
        float eta = 0.01f;
        int epoch = 1000;
        int batch_size = 8;
        int interval = 100;


        // -- 学習と経過の記録 --
        int n_batch = n_train /batch_size; //  1エポックあたりのバッチ数

        middleLayer1 = new MiddleLayer(n_in, n_mid);
        middleLayer2 = new MiddleLayer(n_mid, n_mid);
        outputLayer = new OutputLayer(n_mid, n_out);


        for (int i = 0;i < epoch;i++) {
            //  -- 誤差の計測 --
            forwardPropagation(inputTrain);
            float error_train;
            forwardPropagation(inputTest);
            float error_test;

            int[] indexRandom = MyNumArray.arange(n_train);
            MyNumArray.rand().shuffle(indexRandom);

            // -- 経過の表示 --
//            if (i % interval == 0) {
//                System.out.format("Epoch:%d/%d Error_train: %f Error_test:", error_train, error_test);
//            }
            for (int j = 0;j < n_batch;j++) {
                ArrayWarp mb_index = new ArrayWarp(indexRandom, j * batch_size, (j + 1) * batch_size);
                WarpMyNumArray x = new WarpMyNumArray(inputTrain, mb_index);
                WarpMyNumArray t = new WarpMyNumArray(correctTrain, mb_index);
                forwardPropagation(x);
                backPropagation(t);
                updateWb(eta);
            }

        }
    }
}
