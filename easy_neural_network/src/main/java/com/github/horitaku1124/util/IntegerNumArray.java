package com.github.horitaku1124.util;

public class IntegerNumArray extends MyNumBase<Integer> {
    public IntegerNumArray(int... args) {
        super(args);
    }
    public IntegerNumArray(Integer[] data) {
        super(data);
    }
    public IntegerNumArray(Integer[][] data) {
        super(data);
    }

    @Override
    Integer[] initializeData(int size) {
        return new Integer[size];
    }

    @Override
    Integer[] initializeData(Integer[] data) {
        Integer[] data2 = new Integer[data.length];
        int index = 0;
        for (Integer aData : data) {
            data2[index] = aData;
            index++;
        }
        return data2;
    }

    @Override
    Integer[] initializeData(Integer[][] data) {
        int size = data.length * data[0].length;
        Integer[] data2  = new Integer[size];
        int index = 0;
        for (Integer[] aData : data) {
            for (Integer anAData : aData) {
                data2[index] = anAData;
                index++;
            }
        }

        return data2;
    }

    @Override
    MyNumBase newInstance(int... shapeDefinition) {
        IntegerNumArray instance = new IntegerNumArray(shapeDefinition);
        return instance;
    }


    public IntegerNumArray dot(IntegerNumArray target) {
        if (ndim == 1 && target.ndim == 1) {
            int sum = 0;
            int num = layerLength(0);
            for (int i = 0;i < num;i++) {
                sum += internalData[i] * target.internalData[i];
            }
            IntegerNumArray result = new IntegerNumArray(1);
            result.set(sum, 0);
            return result;
        }

        int num1 = layerLength(0);
        int num2 = layerLength(1);
        int targetNum1 = target.layerLength(0);
        boolean isVertical = false;
        if (target.ndim == 2) {
            int targetNum2 = target.layerLength(1);
            if (targetNum2 == 1) {
                target = (IntegerNumArray) target.reshape(targetNum1);
                isVertical = true;
            }
        }
        if (isVertical) {
            IntegerNumArray ret = new IntegerNumArray(num1);
            for (int i = 0;i < num1;i++) {
                int sum = 0;
                for (int j = 0;j < num2;j++) {
                    sum += get(i, j) * target.get(j);
                }
                ret.set(sum, i);
            }
            return ret;
        } else {
            IntegerNumArray ret = new IntegerNumArray(num1, target.layerLength(1));
            for (int i = 0;i < num1;i++) {
                for (int j = 0;j < target.layerLength(1);j++) {
                    int value = 0;
                    for (int k = 0;k < num2;k++) {
                        value += get(i, k) * target.get(k, j);
                    }
                    ret.set(value, i, j);
                }
            }

            return ret;
        }
    }


    public void printLayer(int... layer) {
        int printLength = ndim - layer.length;
        if (printLength == 0) {
            throw new RuntimeException("ndim = " + ndim + " layer.length = " + layer.length);
        }
        int[] getLayer = new int[ndim];
        for (int l = 0;l < layer.length;l++) {
            getLayer[l] = layer[l];
        }
        if (printLength == 1) {
            int len = layerLength(layer.length);
            StringBuilder sb = new StringBuilder();
            for (int i = 0;i < len;i++) {
                getLayer[getLayer.length - 1] = i;
                if (i != 0) {
                    sb.append(" ");
                }
                sb.append(get(getLayer));
            }
            System.out.println(sb.toString());
        }
        if (printLength == 2) {
            int len1 = layerLength(layer.length);
            int len2 = layerLength(layer.length + 1);
            StringBuilder sb = new StringBuilder();
            for (int i = 0;i < len1;i++) {
                getLayer[getLayer.length - 2] = i;
                for (int j = 0;j < len2;j++) {
                    getLayer[getLayer.length - 1] = j;
                    if (j != 0) {
                        sb.append(" ");
                    }
                    String str = String.format("%d", get(getLayer));
                    sb.append(str);
                }
                sb.append("\n");
            }
            System.out.println(sb.toString());
        }
    }
}
