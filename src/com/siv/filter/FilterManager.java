package com.siv.filter;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Ilya
 */
public class FilterManager {

    private final ReentrantLock lock = new ReentrantLock();
    private final HashMap<FilterType, Filter> filters = new HashMap<>();
    private Filter faceFilter;

    public BufferedImage applyFilters(BufferedImage image) {
        int[][] imageArr = convertImageToArray(image);
        lock.lock();
        try {
            for (Filter f : filters.values()) {
                imageArr = f.applyFilter(imageArr);
            }
            if (faceFilter != null) {
                imageArr = faceFilter.applyFilter(imageArr);
            }
        } finally {
            lock.unlock();
        }
        return convertArrayToImage(imageArr);
    }

    public void addFilter(Filter filter) {
        if (filter == null) {
            return;
        }
        lock.lock();
        try {
            switch (filter.getType()) {
                case FACE_DETECTION:
                    faceFilter = filter;
                    break;
                default:
                    filters.put(filter.getType(), filter);
            }
        } finally {
            lock.unlock();
        }
    }

    public void removeFilter(FilterType type) {
        if (type == null) {
            return;
        }
        lock.lock();
        try {
            switch (type) {
                case FACE_DETECTION:
                    faceFilter = null;
                    break;
                default:
                    filters.remove(type);
            }
        } finally {
            lock.unlock();
        }
    }

    public static int[][] convertImageToArray(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();
        int[][] result = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                result[row][col] = image.getRGB(col, row);
            }
        }

        return result;
    }

    public static BufferedImage convertArrayToImage(int[][] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        final int height = array.length;
        final int weight = array[0].length;
        BufferedImage image = new BufferedImage(weight, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < weight; x++) {
                image.setRGB(x, y, array[y][x]);
            }
        }
        return image;
    }

}
