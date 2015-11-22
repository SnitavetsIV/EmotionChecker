package com.siv.filter;

import java.awt.image.BufferedImage;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Ilya
 */
public class FilterManager {

    private final ReentrantLock lock = new ReentrantLock();
    
    public BufferedImage apply(BufferedImage image) {
        return image;
    }
    
    public void addFilter(FilterType type) {
        
    }
    
    public void removeFilter(FilterType type) {
        
    }
    
}
