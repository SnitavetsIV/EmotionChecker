package com.siv.filter;

import com.siv.util.MatrixUtil;
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
        
        image = MatrixUtil.increaseSize(image, w);
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
        return MatrixUtil.decreaseSize(res, w);
    }
    
}
