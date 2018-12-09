package com.github.horitaku1124.mnist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.horitaku1124.util.Calculation;
import com.github.horitaku1124.util.MnistReader;
import com.github.horitaku1124.util.MyNumArray;
import com.github.horitaku1124.b2chapter4.OutputData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestMnist2 {
    public static void main(String[] args) throws IOException {
        MnistReader reader = new MnistReader();
        MyNumArray images = reader.readImageData("./dataset/t10k-images-idx3-ubyte");
        MyNumArray labels = reader.readLabelData("./dataset/t10k-labels-idx1-ubyte");


        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("result3.json"));) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        OutputData trained = mapper.readValue(sb.toString(), OutputData.class);

        for (int l = 0;l < 100;l++) {
            MyNumArray a2array = new MyNumArray(trained.hiddenWeights.layerLength(0));
            MyNumArray az2array = new MyNumArray(trained.hiddenWeights.layerLength(0));
            for (int i = 0; i < trained.hiddenWeights.layerLength(0); i++) {
                float z2 = trained.hiddenWeights.sumProductRank3x3(i, images, l);
                z2 += trained.hiddenBiases.get(i);
                float a2 = Calculation.calculateOutputA(z2); // a2i
                a2array.set(a2, i);
                float az2 = a2 * (1 - a2); // a'(z2i)
                az2array.set(az2, i);
            }

            MyNumArray z3array = new MyNumArray(trained.outputWeights.layerLength(0));
            int maxIndex = -1;
            float maxValue = 0;
            for (int i = 0; i < trained.outputWeights.layerLength(0); i++) {
                float z3 = a2array.sumProductRank1x2(trained.outputWeights, i) + trained.outputBiases.get(i);
                z3array.set(z3, i);
                float a3 = Calculation.calculateOutputA(z3);
                if (maxValue < a3) {
                    maxIndex = i;
                    maxValue = a3;
                }
//                System.out.print(a3 + " ");
            }
            System.out.println("I " + maxIndex + " by " + maxValue);
            maxIndex = -1;
            maxValue = 0;
            for (int i = 0; i < labels.layerLength(1); i++) {
                float value = labels.get(l, i);
                if (maxValue < value) {
                    maxIndex = i;
                    maxValue = value;
                }
//                System.out.print(a3 + " ");
            }
            System.out.println("A " + maxIndex);
        }
    }
}
