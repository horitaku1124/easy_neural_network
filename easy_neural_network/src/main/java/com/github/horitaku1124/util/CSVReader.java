package com.github.horitaku1124.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CSVReader implements Closeable {
    private BufferedReader fileReader;
    private int header = 0;

    public CSVReader(String path, int header) throws IOException {
        fileReader = Files.newBufferedReader(Paths.get(path));
        this.header = header;
    }

    public String[] read() throws IOException {
        String line = fileReader.readLine();
        if (line == null) {
            return null;
        }
        return line.split(",");
    }
    public <R> List<R[]> readAll(Function<String, R[]> function) throws IOException {
        List<R[]> data = new ArrayList<>();
        int num = 0;
        while(true) {
            String line = fileReader.readLine();
            if (line == null) {
                break;
            }
            if (num != header - 1) {
                data.add(function.apply(line));
            }
            num++;
        }
        return data;
    }

    public MyNumArray readToMuyNum(Function<String, Float[]> function, boolean flip) throws IOException {
        List<Float[]> data = readAll(function);
        Float[] r = data.get(0);
        MyNumArray data2;
        if (r.length == 1) {
            data2 = new MyNumArray(data.size());
        } else {
            data2 = new MyNumArray(data.size(), r.length);
        }
        if (flip) {
            List<Float[]> newData = new ArrayList<>();
            for (int i = data.size() - 1;i >= 0;i--) {
                newData.add(data.get(i));
            }
            data = newData;
        }

        int index = 0;
        for (Float[] line : data) {
            for (int j = 0; j < r.length; j++) {
                data2.internalData[index] = line[j];
                index++;
            }
        }
        return data2;
    }
    public MyNumArray readToMuyNum(Function<String, Float[]> function) throws IOException {
        return readToMuyNum(function, false);

    }

    @Override
    public void close() throws IOException {
        fileReader.close();
    }
}
