package com.github.horitaku1124.mnist;

import com.github.horitaku1124.util.BinaryReader;

import java.io.FileInputStream;
import java.io.IOException;

public class ImageRead {
    public static void main(String[] args) {

        BinaryReader br = new BinaryReader(BinaryReader.Endian.Big);
        try (FileInputStream fs = new FileInputStream("./dataset/t10k-images-idx3-ubyte")) {;

            byte[] buf = new byte[16];
            fs.read(buf);
            long magicNumber = br.bytesToLong(buf, 0);
            long imageNumber = br.bytesToLong(buf, 4);
            long rowsNumber = br.bytesToLong(buf, 8);
            long columnsNumber = br.bytesToLong(buf, 12);
            System.out.println(magicNumber);
            System.out.println(imageNumber);
            System.out.println(rowsNumber);
            System.out.println(columnsNumber);
            int imageSize = (int) (rowsNumber * columnsNumber);
            byte[] img = new byte[imageSize];
            fs.read(img);
            for (int i = 0;i < rowsNumber;i++) {
                for (int j = 0;j < columnsNumber;j++) {
                    int b = img[(int) (i * rowsNumber + j)];
                    System.out.format("%02x ", (b & 0xff));
                }
                System.out.print("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
