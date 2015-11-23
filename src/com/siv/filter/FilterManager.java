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
    private HashMap<FilterType, Filter> filters = new HashMap<>();
    
    public BufferedImage applyFilters(BufferedImage image) {
        return image;
    }
    
    public void addFilter(Filter filter) {
        
    }
    
    public void removeFilter(Filter filter) {
        
    }
    
}
