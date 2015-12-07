package com.siv.detecton;

/**
 *
 * @author Ilya
 */
public class TreeThread extends Thread {

    private final Tree t;
    private float value;
    private final int[][] grayImage;
    private final int[][] squares;
    private final int i;
    private final int j;
    private final float scale;

    public TreeThread(Tree t, int[][] grayImage, int[][] squares, int i, int j, float scale) {
        this.t = t;
        this.grayImage = grayImage;
        this.squares = squares;
        this.i = i;
        this.j = j;
        this.scale = scale;
    }

    @Override
    public void run() {
        value = t.getVal(grayImage, squares, i, j, scale);
    }

    public float getValue() {
        return value;
    }

}
