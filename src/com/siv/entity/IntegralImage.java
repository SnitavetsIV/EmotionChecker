package com.siv.entity;

import com.siv.util.FileUtil;
import java.awt.image.BufferedImage;

/**
 *
 * @author Ilya
 */
public class IntegralImage extends ArrayImage {

    private final double[][] integral;

    public IntegralImage(String path) {
        this(FileUtil.readImage(path));
    }

    public IntegralImage(BufferedImage image) {
        super(image);
        double[][] res = new double[h][w];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int qwe = arr[y][x];
                int r = (qwe << 8) >>> 24;
                int g = (qwe << 16) >>> 24;
                int b = (qwe << 24) >>> 24;
                double value = 0.257 * r + 0.504 * g + 0.098 * b;
                if (x > 0) {
                    value = value + res[y][x - 1];
                }
                if (y > 0) {
                    value = value + res[y - 1][x];
                }
                if (x > 0 && y > 0) {
                    value = value - res[y - 1][x - 1];
                }
                res[y][x] = value;
            }
        }
        integral = res;
    }

    public int sumValue(Rect r) {
        int x = r.getX();
        int y = r.getY();
        int x2 = x + r.getWidth() - 1;
        int y2 = y + r.getHeight() - 1;
        return (int) (integral[x2][y2] - (x > 0 ? integral[x - 1][y2] : 0)
                - (y > 0 ? integral[x2][y - 1] : 0)
                + (x > 0 && y > 0 ? integral[x - 1][y - 1] : 0));
    }

}
