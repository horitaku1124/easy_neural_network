package com.github.horitaku1124.util;

import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class MyNumArray {
    public int ndim;
    public long size;
    public int[] shape;
    public float[] internalData;
    public MyNumArray() {}

    public MyNumArray(float[] data) {
        ndim = 1;
        size = data.length;
        shape = new int[]{data.length};
        internalData = new float[(int) size];
        int index = 0;
        for (float aData : data) {
            internalData[index] = aData;
            index++;
        }
    }
    public MyNumArray(float[][] data) {
        ndim = 2;
        size = data.length * data[0].length;
        internalData = new float[(int) size];
        shape = new int[] {data.length, data[0].length};
        int index = 0;
        for (float[] aData : data) {
            for (float anAData : aData) {
                internalData[index] = anAData;
                index++;
            }
        }
    }

    public MyNumArray(float[][][] data) {
        ndim = 3;
        size = data.length * data[0].length * data[0][0].length;
        shape = new int[] {data.length, data[0].length, data[0][0].length};
        internalData = new float[(int) size];
        int index = 0;
        for (float[][] aData : data) {
            for (float[] anAData : aData) {
                for (float anAnAData : anAData) {
                    internalData[index] = anAnAData;
                    index++;
                }
            }
        }
    }

    public MyNumArray(int... shapeDefinition) {
        ndim = shapeDefinition.length;
        size = 1;
        for (int num: shapeDefinition) {
            size *= num;
        }
        shape = shapeDefinition;
        internalData = new float[(int) size];
    }


    public int size() {
        return (int)size;
    }

    public int layerLength(int dimension) {
        return shape[dimension];
    }

    public void dataCopy(byte[] data) {
        dataCopy(data, 0);
    }
    public void dataCopy(byte[] data, int index) {
        for (int i = 0;i < data.length;i++) {
            internalData[index + i] = (float)(data[i] & 0xff);
        }
    }

    public int offsetToIndex(int... offset) {
        int index = -1;
        if (ndim == 1) {
            index = offset[0];
        } else if (ndim == 2) {
            index = offset[0] * shape[1] + offset[1];
        } else if (ndim == 3) {
            index = offset[0] * shape[1] * shape[2] + offset[1] * shape[2] + offset[2];
        } else if (ndim == 4) {
            index = offset[0] * shape[1] * shape[2] * shape[3] +
                    offset[1] * shape[2] * shape[3] +
                    offset[2] * shape[3] +
                    offset[3];
        }
        if (index >= internalData.length) {
            for (int i = 0;i < offset.length;i++) {
                System.out.println("offset[" + i + "]=" + offset[i]);
            }
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return index;
    }

    public void set(float value, int... offset) {
        internalData[offsetToIndex(offset)] = value;
    }

    public float get(int... offset) {
        return internalData[offsetToIndex(offset)];
    }

    public float SUMXMY2(MyNumArray target, int arrayIndex) {
        int thisOffset = arrayIndex * shape[1];
        int thatOffset = arrayIndex * target.shape[1];
        int limit = shape[1];
        float sum = 0;
        for (int i = 0;i < limit;i++) {
            sum += Math.pow(internalData[i + thisOffset] - target.internalData[i + thatOffset], 2);
        }
        return sum;
    }

    public float sumProductRank2x2(int offset, MyNumArray target, int targetLayer0index) {
        int num = layerLength(1);
        int thisOffset = offset * num;
        int targetLayerOffset = targetLayer0index * target.layerLength(1);
        float sum = 0;
        for (int i = 0;i < num;i++) {
            sum += internalData[i + thisOffset] * target.internalData[i + targetLayerOffset];
        }
        return sum;
    }
    public float sumProductRank1x2(MyNumArray target, int targetLayer0index) {
        int num = (int) size;
        int targetLayerOffset = targetLayer0index * target.layerLength(1);
        float sum = 0;
        for (int i = 0;i < num;i++) {
            sum += internalData[i] * target.internalData[i + targetLayerOffset];
        }
        return sum;
    }
    public float sumProductRank3x3(int offset, MyNumArray target, int targetLayer0index) {
        int num = layerLength(1) * layerLength(2);
        int thisOffset = offset * num;
        int targetLayerOffset = targetLayer0index * target.layerLength(1) * target.layerLength(2);
        float sum = 0;
        for (int i = 0;i < num;i++) {
            sum += internalData[i + thisOffset] * target.internalData[i + targetLayerOffset];
        }
        return sum;
    }

    public MyNumArray mmulti(MyNumArray s3array) {
        int num1 = layerLength(0);
        int num2 = layerLength(1);
        MyNumArray ret = new MyNumArray(num2);
        for (int i = 0;i < num2;i++) {
            float sum = 0;
            for (int j = 0;j < num1;j++) {
                sum += get(j, i) * s3array.get(j);
            }
            ret.set(sum, i);
        }
        return ret;
    }

    public void broadcastAdd(float num) {
        for (int i = 0;i < size;i++) {
            internalData[i] += num;
        }
    }

    public MyNumArray broadcastSub(MyNumArray num) {
        MyNumArray result = new MyNumArray(shape);
        if (ndim == 2 && num.ndim == 1) {
            int d0 = layerLength(0);
            int d1 = layerLength(1);
            for (int i = 0;i < d0;i++) {
                for (int j = 0;j < d1;j++) {
                    float sub = this.get(i, j) - num.get(j);
                    result.set(sub, i, j);
                }
            }
        }
        return result;
    }

    public MyNumArray broadcastDivide(MyNumArray num) {
        MyNumArray result = new MyNumArray(shape);
        if (ndim == 2 && num.ndim == 1) {
            int d0 = layerLength(0);
            int d1 = layerLength(1);
            for (int i = 0;i < d0;i++) {
                for (int j = 0;j < d1;j++) {
                    float divide = this.get(i, j) / num.get(j);
                    result.set(divide, i, j);
                }
            }
        }
        return result;
    }

    public MyNumArray broadcastDivide(float num) {
        MyNumArray result = new MyNumArray(shape);

        for (int i = 0;i < size;i++) {
            result.internalData[i] = internalData[i] / num;
        }
        return result;
    }

    public static MyNumArray broadcastDivide(float num, MyNumArray div) {
        MyNumArray result = new MyNumArray(div.shape);

        for (int i = 0;i < result.size();i++) {
            result.internalData[i] = num / div.internalData[i];
        }
        return result;
    }

    public static MyNumArray rand(int... shapes) {
        MyNumArray mna = new MyNumArray(shapes);
        for (int i = 0;i < mna.size();i++) {
            mna.internalData[i] = (float) Math.random();
        }
        return mna;
    }
    public static MyNumArray zeros(int... shapes) {
        MyNumArray mna = new MyNumArray(shapes);
        for (int i = 0;i < mna.size();i++) {
            mna.internalData[i] = 0;
        }
        return mna;
    }

    public static int[] arange(int num, IntPredicate filter) {
        return IntStream.range(0, num).filter(filter).toArray();
    }

    public static int[] arange(int num) {
        return IntStream.range(0, num).toArray();
    }

    public static MyNumArray average(MyNumArray input, int axis) {
        int wide = input.layerLength(1);
        MyNumArray average = new MyNumArray(wide);
        double[] sum = new double[wide];
        for (int i = 0;i < wide;i++) {
            sum[i] = 0;
        }
        int len = input.layerLength(0);
        for (int i = 0;i < len;i++) {
            for (int j = 0;j < wide;j++) {
                sum[j] += input.get(i, j);
            }
        }

        for (int i = 0;i < wide;i++) {
            average.set((float) (sum[i] / len), i);
        }

        return average;
    }

    public MyNumArray getIn(int[] index) {
        MyNumArray result;
        if (ndim == 1) {
             result = new MyNumArray(index.length);
             for (int i = 0;i < index.length;i++) {
                 float value = get(index[i]);
                 result.set(value, i);
             }
            return result;
        }
        if (ndim == 2) {
             result = new MyNumArray(index.length, layerLength(1));
             for (int i = 0;i < index.length;i++) {
                 for (int j = 0;j < layerLength(1);j++) {
                     float value = get(index[i], j);
                     result.set(value, i, j);
                 }
             }
             return result;
        }
        return null;
    }

    public MyNumArray multiply(MyNumArray multi) {
        MyNumArray result = new MyNumArray(shape);
        for (int i = 0;i < size;i++) {
            result.internalData[i] = this.internalData[i] * multi.internalData[i];
        }
        return result;
    }

    public MyNumArray add(MyNumArray multi) {
        MyNumArray result = new MyNumArray(shape);
        for (int i = 0;i < size;i++) {
            result.internalData[i] = this.internalData[i] + multi.internalData[i];
        }
        return result;
    }

    public MyNumArray minus(MyNumArray multi) {
        MyNumArray result = new MyNumArray(shape);
        for (int i = 0;i < size;i++) {
            result.internalData[i] = this.internalData[i] - multi.internalData[i];
        }
        return result;
    }

    public MyNumArray divide(MyNumArray divide) {
        MyNumArray ret = new MyNumArray(divide.shape);
        for (int i = 0;i < divide.size;i++) {
            ret.internalData[i] = internalData[i] / divide.internalData[i];
        }
        return ret;
    }


    public static MyNumArray std(MyNumArray input, int axis) {
        int len = input.layerLength(0);
        int wide = input.layerLength(1);
        double[] division = new double[wide];
        MyNumArray average = average(input, axis);
        for (int i = 0;i < len;i++) {
            for (int j = 0;j < wide;j++) {
                float diff = input.get(i, j) - Math.abs(average.get(j));
                division[j] += diff * diff;
            }
        }
        MyNumArray std = new MyNumArray(wide);

        for (int i = 0;i < wide;i++) {
            std.set((float) Math.sqrt(division[i] / len), i);
        }
        return std;
    }

    public static MyNumArray sqrt(MyNumArray input) {
        MyNumArray sqrt = new MyNumArray(input.shape);

        for (int i = 0;i < input.size;i++) {
            sqrt.internalData[i] = (float) Math.sqrt(input.internalData[i]);
        }
        return sqrt;
    }

    public MyNumArray reshape(int... shape) {
        int length = 1;
        for (int i: shape) {
            length *= i;
        }
        if (length != size) {
            throw new RuntimeException("shape size mismatch");
        }
        MyNumArray newArray = new MyNumArray(shape);
        if (size >= 0) System.arraycopy(this.internalData,
                0, newArray.internalData, 0, Math.toIntExact(size));
        return newArray;
    }

    public MyNumArray dot(MyNumArray target) {
        int num1 = layerLength(0);
        int num2 = layerLength(1);
        int targetNum1 = target.layerLength(0);
        boolean isVertical = false;
        if (target.ndim == 2) {
            int targetNum2 = target.layerLength(1);
            if (targetNum2 == 1) {
                target = target.reshape(targetNum1);
                isVertical = true;
            }
        }
        if (isVertical) {
            MyNumArray ret = new MyNumArray(num1);
            for (int i = 0;i < num1;i++) {
                int sum = 0;
                for (int j = 0;j < num2;j++) {
                    sum += get(i, j) * target.get(j);
                }
                ret.set(sum, i);
            }
            return ret;
        } else {
            MyNumArray ret = new MyNumArray(num1, target.layerLength(1));
            for (int i = 0;i < num1;i++) {
                for (int j = 0;j < target.layerLength(1);j++) {
                    float value = 0;
                    for (int k = 0;k < num2;k++) {
                        value += get(i, k) * target.get(k, j);
                    }
                    ret.set(value, i, j);
                }
            }
            return ret;
        }
    }

    public MyNumArray where(Function<Float, Float> filter) {
        MyNumArray result = new MyNumArray(this.shape);
        for (int i = 0;i < size;i++) {
            result.internalData[i] = filter.apply(this.internalData[i]);
        }
        return result;
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
                    String str = String.format("%1.2f", get(getLayer));
                    if ("0.00".equals(str)) {
                        str = "0  ";
                    } else if (str.startsWith("0.")) {
                        str = str.replace("0.", ".");
                    } else if ("1.00".equals(str)) {
                        str = "1.0";
                    }
                    sb.append(str);
                }
                sb.append("\n");
            }
            System.out.println(sb.toString());
        }
    }

    public MyNumArray transpose() {
        MyNumArray result = new MyNumArray(shape[1], shape[0]);
        for (int i = 0;i < shape[0];i++) {
            for (int j = 0;j < shape[1];j++) {
                result.set(this.get(i, j), j, i);
            }
        }
        return result;
    }

    public static MyNumArray sum(MyNumArray x) {
        return sum(x, null, false);
    }

    public static MyNumArray sum(MyNumArray x, Integer axis, boolean keepDims) {
        MyNumArray ret = null;
        if (axis == null) {
            ret = keepDims ? new MyNumArray(x.shape) : new MyNumArray(1);
            float sum = 0;
            for (int i = 0;i < x.size;i++) {
                sum += x.internalData[i];
            }
            ret.internalData[0] = sum;
        } else if (axis == 0) {
            ret = keepDims ? new MyNumArray(x.shape) : new MyNumArray(x.shape[1]);
            for (int i = 0;i < x.shape[1];i++) {
                float sum = 0;
                for (int j = 0;j < x.shape[0];j++) {
                    sum += x.get(j, i);
                }
                if (keepDims) {
                    ret.set(sum, 0, i);
                } else {
                    ret.set(sum, i);
                }
            }
        } else if (axis == 1) {
            ret = keepDims ? new MyNumArray(x.shape) : new MyNumArray(x.shape[0]);
            for (int i = 0;i < x.shape[0];i++) {
                float sum = 0;
                for (int j = 0;j < x.shape[1];j++) {
                    sum += x.get(i, j);
                }
                if (keepDims) {
                    ret.set(sum, 0, i);
                } else {
                    ret.set(sum, i);
                }
            }
        }
        return ret;
    }

    public static MyNumArray exp(MyNumArray x) {
        MyNumArray ret = new MyNumArray(x.shape);
        for (int i = 0;i < ret.size;i++) {
            ret.internalData[i] = (float) Math.pow(Math.E, x.internalData[i]);
        }
        return ret;
    }

    public void shuffle(int[] array) {
        float[] sortGuide = new float[(int) size];
        for (int i = 0;i < size;i++) {
            sortGuide[i] = (float) Math.random();
        }

        for (int i = 0;i < size - 1;size++) {
            for (int j = i + 1;j < size;j++) {
                if (sortGuide[i] > sortGuide[j]) {
                    int temp1 = array[i];
                    array[i] = array[j];
                    array[j] = temp1;

                    float temp = sortGuide[i];
                    sortGuide[i] = sortGuide[j];
                    sortGuide[j] = temp;
                }
            }
        }
    }
}
