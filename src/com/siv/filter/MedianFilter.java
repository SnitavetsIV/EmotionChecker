package com.siv.filter;

import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 *
 * @author Ilya
 */
public class MedianFilter implements Filter{

    private int w = 3;
    
    public MedianFilter() {
    }
    
    public MedianFilter(int w) {
        this.w = w;
    }
    
    @Override
    public FilterType getType() {
        return FilterType.MEDIAN;
    }

    @Override
    public int[][] applyFilter(int[][] image) {
        image = increaseSize(image);
        final int height = image.length;
        final int width = image[0].length;
        final int w2 = w / 2;
        final int ww = w * w;
        final int indexArr = (w != 1) ? (ww - 1) / 2 + 1 : 0;
        int[][] res = new int[height][width];
        for (int y = w2; y < height - w2; y++) {
            for (int x = w2; x < width - w2; x++) {
                int[] ar = new int[ww];
                int i = 0;
                for (int xx = -w2; xx <= w2; xx++) {
                    for (int yy = -w2; yy <= w2; yy++) {
                        ar[i] = image[y + yy][x + xx];
                        i++;
                    }
                }
                Arrays.sort(ar);
                res[y][x] = ar[indexArr];
            }

        }
        return decreaseSize(res);
    }
    
    protected int[][] increaseSize(int[][] image) {

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

    protected int[][] decreaseSize(int[][] array) {
        int w2 = 2 * w;
        int height = array.length - w2;
        int width = array[0].length - w2;
        int[][] res = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                res[y][x] = array[y + w2][x + w2];
            }
        }
        return res;
    }
    
}
