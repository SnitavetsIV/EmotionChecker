package com.siv.filter;

/**
 *
 * @author Ilya
 */
public class GausFilter extends MatrixFilter {

    private final int w = 5;
    private final double[][] matrix = 
        {{0.000789, 0.006581, 0.013347, 0.006581, 0.000789},
         {0.006581, 0.054901, 0.111345, 0.054901, 0.006581},
         {0.013347, 0.111345, 0.225821, 0.111345, 0.013347},
         {0.006581, 0.054901, 0.111345, 0.054901, 0.006581},
         {0.000789, 0.006581, 0.013347, 0.006581, 0.000789}};
    
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
        return FilterType.GAUS;
    }

}
