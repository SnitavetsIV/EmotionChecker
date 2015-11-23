package com.siv.filter;

import com.siv.detecton.Detector;
import java.awt.Color;
import java.awt.Rectangle;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ilya
 */
public class FaceFilter implements Filter {

    @Override
    public int[][] applyFilter(int[][] image) {
        try {
            Detector d = new Detector("resources\\haarcascade_frontalface_default.xml");
            List<Rectangle> faces = d.getFaces(image, 1, 1.25f, 0.1f, 1, false);
            for (Rectangle r : faces) {
                for (int x = r.x; x <= r.x + r.width; x++) {
                    image[r.y][x] = Color.black.getRGB();
                }
                for (int x = r.x; x <= r.x + r.width; x++) {
                    image[r.y + r.height][x] = Color.black.getRGB();
                }
                for (int y = r.y; y <= r.y + r.height; y++) {
                    image[y][r.x] = Color.black.getRGB();
                }
                for (int y = r.y; y <= r.y + r.height; y++) {
                    image[y][r.x + r.width] = Color.black.getRGB();
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FaceFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return image;
    }

    @Override
    public FilterType getType() {
        return FilterType.FACE_DETECTION;
    }

}
