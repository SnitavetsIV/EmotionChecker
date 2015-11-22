package com.siv.entity;

import java.awt.image.BufferedImage;

/**
 *
 * @author Ilya
 */
public class IntegralImage extends ArrayImage {

    public IntegralImage(String path) {
        super(path);
    }

    public IntegralImage(BufferedImage image) {
        super(image);
    }
    
}
