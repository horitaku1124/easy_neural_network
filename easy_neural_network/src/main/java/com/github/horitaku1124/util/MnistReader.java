package com.github.horitaku1124.util;

import java.io.FileInputStream;
import java.io.IOException;

public class MnistReader {
    public MyNumArray readImageData(String filePath) throws IOException {
        BinaryReader br = BinaryReader.getBigEndianReader();
        float[][][] allImageData;
        try (FileInputStream fs = new FileInputStream(filePath)) {
            byte[] buf = new byte[16];
            fs.read(buf);
            long magicNumber = br.bytesToLong(buf, 0);
            int imageNumber = (int) br.bytesToLong(buf, 4);
            int rowsNumber = (int) br.bytesToLong(buf, 8);
            int columnsNumber = (int) br.bytesToLong(buf, 12);
//            System.out.println(magicNumber);
//            System.out.println(imageNumber);
//            System.out.println(rowsNumber);
//            System.out.println(columnsNumber);
            int imageSize = rowsNumber * columnsNumber;
            allImageData = new float[imageNumber][rowsNumber][columnsNumber];
            byte[] img = new byte[imageSize];
            for (int i = 0;i < imageNumber / 10;i++) {
                fs.read(img);
                for (int x = 0;x < rowsNumber;x++) {
                    for (int y = 0;y < columnsNumber;y++) {
                        int b = img[x * rowsNumber + y];
                        allImageData[i][x][y] = (float)(b & 0xff) / 255.0f;
                    }
                }
            }
        }
        return new MyNumArray(allImageData);
    }

    public MyNumArray readLabelData(String filePath) throws IOException {
        BinaryReader br = BinaryReader.getBigEndianReader();
        float[][] answerLayer;
        try (FileInputStream fs = new FileInputStream(filePath)) {
            byte[] buf = new byte[8];
            fs.read(buf);
            long magicNumber = br.bytesToLong(buf, 0);
            int labelNumber = (int) br.bytesToLong(buf, 4);
            answerLayer = new float[labelNumber][2];
//            System.out.println(magicNumber);
//            System.out.println(labelNumber);
            byte[] labels = new byte[labelNumber];
            fs.read(labels);
            for (int i = 0;i < labels.length / 10;i++) {
                if (labels[i] < 2) {
                    answerLayer[i][labels[i]] = 1f;
                }
            }
        }
        return new MyNumArray(answerLayer);
    }
}
