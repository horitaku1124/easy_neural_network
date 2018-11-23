package com.github.horitaku1124.b2chapter4;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.horitaku1124.util.MyNumArray;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestB24 {
    public static MyNumArray inputLayers;
    public static MyNumArray answerLayers;
    private static void setupData() {
        float[][][] _inputLayers = new float[][][] {
                {
                        {0f,1f,0f},
                        {1f,0f,1f},
                        {1f,0f,1f},
                        {1f,0f,1f},
                },
                {
                        {0f,1f,1f},
                        {0f,1f,0f},
                        {0f,1f,0f},
                        {0f,1f,0f},
                },
        };
        inputLayers = new MyNumArray(_inputLayers);

    }

    private static float calculateOutputA(float z2) {
        return 1.0f / (1.0f + (float)Math.exp(-z2));
    }

    public static void main(String[] args) throws IOException {

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("result2.json"));) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        OutputData trained = mapper.readValue(sb.toString(), OutputData.class);

        setupData();

        for (int l = 0;l < inputLayers.layerLength(0);l++) {
            MyNumArray a2array =  new MyNumArray(trained.hiddenWeights.layerLength(0));
            MyNumArray az2array =  new MyNumArray(trained.hiddenWeights.layerLength(0));
            for (int i = 0; i < trained.hiddenWeights.layerLength(0); i++) {
                float z2 = trained.hiddenWeights.sumProductRank3x3(i, inputLayers, l);
                z2 += trained.hiddenBiases.get(i);
                float a2 = calculateOutputA(z2); // a2i
                a2array.set(a2, i);
                float az2 = a2 * (1 - a2); // a'(z2i)
                az2array.set(az2, i);
            }

            MyNumArray z3array = new MyNumArray(trained.outputWeights.layerLength(0));
            for (int i = 0; i < trained.outputWeights.layerLength(0); i++) {
                float z3 = a2array.sumProductRank1x2(trained.outputWeights, i) + trained.outputBiases.get(i);
                z3array.set(z3, i);
                float a3 = calculateOutputA(z3);
                System.out.println(a3);
            }
        }

    }
}
