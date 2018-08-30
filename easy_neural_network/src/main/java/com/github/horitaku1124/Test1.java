package com.github.horitaku1124;

public class Test1 {
    static float[][][] hl = new float[][][] {
            {
                    {0,1,2,3},
                    {4,5,6,7},
                    {8,9,10,11},
                    {12,13,14,15},
            },
            {
                    {16,17,18,19},
                    {20,21,22,23},
                    {24,25,26,27},
                    {28,29,30,31},
            },
            {
                    {32,33,34,35},
                    {36,37,38,39},
                    {40,41,42,43},
                    {44,45,46,47},
            }
    };
    static int w1 = hl[0].length;
    static int w2 = hl[0][0].length;
    static int w3 = w1 * w2;
    public static float get(long index) {
        int x = (int) (index / w3);
        int y = (int) ((index - (x * w3)) / w2);
        int z = (int) (index % w2);
        System.out.println("x=" + x + " y=" + y + " z=" + z);
        return hl[x][y][z];
    }
    public static void main(String[] args) {
        long length = hl.length * hl[0].length * hl[0][0].length;
        System.out.println(length);
        System.out.println(get(0));
        System.out.println(get(10));
        System.out.println(get(22));
        System.out.println(get(47));
    }
}
