package com.siv.entity;

import com.siv.util.FileUtil;
import java.awt.image.BufferedImage;

/**
 *
 * @author Ilya
 */
public class ArrayImage {
    
    private int w,h;
    private int[][] arr;
    private BufferedImage source;
    
    public ArrayImage(String path) {
        this(FileUtil.readImage(path));
    }
    
    public ArrayImage(BufferedImage image) {
        source = image;
        w = image.getWidth();
        h = image.getHeight();
        arr = new int[h][w];
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                arr[row][col] = image.getRGB(col, row);
            }
        }
    }
    
   public BufferedImage getBufferedImage() {
       return source;
   }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public int[][] getArr() {
        return arr;
    }
    
   
   
}
