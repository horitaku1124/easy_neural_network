package com.github.horitaku1124.dl_sample;

import com.github.horitaku1124.dl_sample.layer.LayerBase;
import com.github.horitaku1124.dl_sample.layer.MiddleLayer;
import com.github.horitaku1124.dl_sample.layer.OutputLayer;
import com.github.horitaku1124.util.CSVReader;
import com.github.horitaku1124.util.MyNumArray;

import java.io.IOException;

public class NNTest {
    MiddleLayer middleLayer1;
    MiddleLayer middleLayer2;
    OutputLayer outputLayer;

    private void forwardPropagation(MyNumArray x) {
        middleLayer1.forward(x);
    }

    public static void main(String[] args) throws IOException {
        MyNumArray irisImageData;
        MyNumArray irisLabelData;
        try(CSVReader csvReader = new CSVReader("./iris.csv", 1);) {
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
        try(CSVReader csvReader = new CSVReader("./iris.csv", 1);) {
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

        int n_train = inputTrain.layerLength(0);
        int n_test = inputTest.layerLength(0);

        int n_in = 4;
        int n_mid = 25;
        int n_out = 3;

        double wb_width = 0.1;
        double eta = 0.01;
        int epoch = 1000;
        int batch_size = 8;
        int interval = 100;

        for (int i = 0;i < epoch;i++) {

        }
    }
}
