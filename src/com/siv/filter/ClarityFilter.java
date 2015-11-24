package com.siv.filter;

/**
 *
 * @author Ilya
 */
public class ClarityFilter extends MatrixFilter {
    
    private final int w = 3;
    private final double[][] matrix = 
        {{-1, -1, -1},
         {-1, 9, -1},
         {-1, -1, -1}};
    
    @Override
    public double[][] getMatrix() {
        return matrix;
    }

    @Override
    public int getWindow() {
        return w;
    }

    @Override
    public FilterType getType() {
        return FilterType.CLARITY;
    }
    
}
