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
public class FaceFilter extends Filter {

    @Override
    public int[][] applyFilter(int[][] image) {
        try {
            Detector d = new Detector("resources\\haarcascade_frontalface_default.xml");
            List<Rectangle> faces = d.getFaces(image, 1, 1.25f, 0.1f, 1, true);
            for (Rectangle r : faces) {
                for (int x = r.x; x <= r.x + r.width; x++) {
                    for (int y = r.y; y< r.y + r.height; y++) {
                        image[y][x] = Color.WHITE.getRGB();
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FaceFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return image;
    }

}
