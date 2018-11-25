package com.github.horitaku1124.mnist;

import com.github.horitaku1124.util.BinaryReader;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * http://yann.lecun.com/exdb/mnist/
 */
public class ImageReadTest {
    public static void main(String[] args) throws IOException {
        BinaryReader br = BinaryReader.getBigEndianReader();
        byte[][][] allImageData;
        try (FileInputStream fs = new FileInputStream("./dataset/t10k-images-idx3-ubyte")) {
            byte[] buf = new byte[16];
            fs.read(buf);
            long magicNumber = br.bytesToLong(buf, 0);
            int imageNumber = (int) br.bytesToLong(buf, 4);
            int rowsNumber = (int) br.bytesToLong(buf, 8);
            int columnsNumber = (int) br.bytesToLong(buf, 12);
            System.out.println(magicNumber);
            System.out.println(imageNumber);
            System.out.println(rowsNumber);
            System.out.println(columnsNumber);
            int imageSize = rowsNumber * columnsNumber;
            allImageData = new byte[imageNumber][rowsNumber][columnsNumber];
            byte[] img = new byte[imageSize];
            for (int i = 0;i < imageNumber;i++) {
                fs.read(img);
                for (int x = 0;x < rowsNumber;x++) {
                    for (int y = 0;y < columnsNumber;y++) {
                        byte b = img[x * rowsNumber + y];
                        allImageData[i][x][y] = b;
                    }
                }
            }
        }
        try (FileInputStream fs = new FileInputStream("./dataset/t10k-labels-idx1-ubyte")) {
            byte[] buf = new byte[8];
            fs.read(buf);
            long magicNumber = br.bytesToLong(buf, 0);
            long labelNumber = br.bytesToLong(buf, 4);
            System.out.println(magicNumber);
            System.out.println(labelNumber);
            byte[] labels = new byte[(int) labelNumber];
            fs.read(labels);
            System.out.println(labels[0]);
        }
    }
}
