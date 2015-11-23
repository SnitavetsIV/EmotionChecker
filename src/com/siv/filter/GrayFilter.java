package com.siv.filter;

/**
 *
 * @author Ilya
 */
public class GrayFilter implements Filter {

    @Override
    public int[][] applyFilter(final int[][] image) {
        final int height = image.length;
        final int width = image[0].length;
        final int[][] res = new int[height][width];
        int value;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                value = image[y][x];
                final int rr = (value << 8) >>> 24;
                final int gg = (value << 16) >>> 24;
                final int bb = (value << 24) >>> 24; 
                final int gray =(int) ((0.2125 * rr) + (0.7154 * gg) + (0.0721 * bb));
                final int color = ((255 & 0xFF) << 24) |
                ((gray & 0xFF) << 16) |
                ((gray & 0xFF) << 8)  |
                ((gray & 0xFF));
                res[y][x] = color;
            }
        }
        return res;
    }

    @Override
    public FilterType getType() {
        return FilterType.GRAY;
    }
    
}
