package com.siv.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class FileUtil {

    public static BufferedImage readImage(String path) {
        if (path == null) {
            return null;
        }
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return img;
    }

}
