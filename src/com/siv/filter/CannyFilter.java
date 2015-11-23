package com.siv.filter;

/**
 *
 * @author Ilya
 */
public class CannyFilter implements Filter{

    @Override
    public FilterType getType() {
        return FilterType.CANNY;
    }

    @Override
    public int[][] applyFilter(int[][] image) {
        return image;
    }
    
}
