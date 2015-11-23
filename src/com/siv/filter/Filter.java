package com.siv.filter;

/**
 *
 * @author Ilya
 */
public interface Filter {

    FilterType getType();
    
    int[][] applyFilter(int[][] image);
    
}
