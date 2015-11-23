package com.siv.filter;

/**
 *
 * @author Ilya
 */
public abstract class Filter {
    
    private FilterType type;

    public FilterType getType() {
        return type;
    }

    public void setType(FilterType type) {
        this.type = type;
    }
    
    public abstract int[][] applyFilter(int[][] image);
    
}
