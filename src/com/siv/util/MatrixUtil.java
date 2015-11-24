package com.siv.util;

/**
 *
 * @author Ilya
 */
public class MatrixUtil {
    
    public static int[][] increaseSize(int[][] image, int w) {

        int[][] ar = image;
        int w2 = w / 2;
        int[][] res = new int[ar.length + 2 * w2][ar[0].length + 2 * w2];
        for (int y = 0; y < ar.length; y++) {
            for (int x = 0; x < ar[y].length; x++) {
                if (x < w2) {
                    res[y + w2][w2 - x - 1] = ar[y][x];
                }
                if (y < w2) {
                    res[w2 - y - 1][x + w2] = ar[y][x];
                }
                if (x < w2 && y < w2) {
                    res[w2 - y - 1][w2 - x - 1] = ar[y][x];
                }
                if (x < w2 && (y >= ar.length - w2)) {
                    res[2 * ar.length - y - 1 + w2][w2 - x - 1] = ar[y][x];
                }
                if (y < w2 && (x >= ar[y].length - w2)) {
                    res[w2 - y - 1][2 * ar[y].length - x - 1 + w2] = ar[y][x];
                }
                res[y + w2][x + w2] = ar[y][x];
                if (x >= ar[y].length - w2) {
                    res[y + w2][2 * ar[y].length - x - 1 + w2] = ar[y][x];
                }
                if (y >= ar.length - w2) {
                    res[2 * ar.length - y - 1 + w2][x + w2] = ar[y][x];
                }
                if ((y >= ar.length - w2) && (x >= ar[y].length - w2)) {
                    res[2 * ar.length - y - 1 + w2][2 * ar[y].length - x - 1 + w2] = ar[y][x];
                }

            }
        }
        return res;
    }

    public static int[][] decreaseSize(int[][] array, int w) {
        int w2 = w / 2;
        int height = array.length - w2 * 2;
        int width = array[0].length - w2 * 2;
        int[][] res = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                res[y][x] = array[y + w2][x + w2];
            }
        }
        return res;
    }
    
}
