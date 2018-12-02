package com.github.horitaku1124.mnist;

import com.github.horitaku1124.util.MnistReader;
import com.github.horitaku1124.util.MyNumArray;

import java.io.IOException;

public class ImageReadTest2 {
    public static void main(String[] args) throws IOException {
        MnistReader reader = new MnistReader();
        MyNumArray images = reader.readImageData("./dataset/t10k-images-idx3-ubyte");
        MyNumArray labels = reader.readLabelData("./dataset/t10k-labels-idx1-ubyte");
//        System.out.println(images.ndim);
//        System.out.println(labels.ndim);

        for (int i = 0;i < 100;i++) {
            images.printLayer(i);
            labels.printLayer(i);
        }
    }
}
