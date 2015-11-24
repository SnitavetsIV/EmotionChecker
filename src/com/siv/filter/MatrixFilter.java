/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siv.filter;

import com.siv.util.MatrixUtil;
import java.awt.Color;

/**
 *
 * @author Ilya
 */
public abstract class MatrixFilter implements Filter {

    public abstract double[][] getMatrix();
    
    public abstract int getWindow(); 
    
    @Override
    public int[][] applyFilter(int[][] image) {
        final int w = getWindow();
        final double[][] matrix = getMatrix();
        
        image = MatrixUtil.increaseSize(image, w);
        
        final int height = image.length;
        final int width = image[0].length;
        final int w2 = w / 2;
        int[][] res = new int[height][width];
        double div = 0;
        for (double[] matrix1 : matrix) {
            for (int x = 0; x < matrix1.length; x++) {
                div += matrix1[x];
            }
        }
        if (Math.abs(div) < 0.0000001) {
            div = 1;
        }
        for (int y = w2; y < height - w2; y++) {
            for (int x = w2; x < width - w2; x++) {
                double r = 0;
                double g = 0;
                double b = 0;
                for (int yy = -w2; yy <= w2; yy++) {
                    for (int xx = -w2; xx <= w2; xx++) {
                        int rr = (image[y + yy][x + xx] << 8) >>> 24;
                        int gg = (image[y + yy][x + xx] << 16) >>> 24;
                        int bb = (image[y + yy][x + xx] << 24) >>> 24;
                        r += rr * matrix[w2 + yy][w2 + xx];
                        g += gg * matrix[w2 + yy][w2 + xx];
                        b += bb * matrix[w2 + yy][w2 + xx];
                    }
                }
                r = (r / div);
                g = (g / div);
                b = (b / div);

                int rr = Math.min(Math.max((int) r, 0), 255);
                int gg = Math.min(Math.max((int) g, 0), 255);
                int bb = Math.min(Math.max((int) b, 0), 255);
                res[y][x] = new Color(rr, gg, bb, 255).getRGB();
            }
        }
        return MatrixUtil.decreaseSize(res, w);
    }

}
