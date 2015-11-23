package com.siv.detecton;

import java.util.ArrayList;

public class Stage {

    ArrayList<Tree> trees;
    float threshold;

    public Stage(float threshold) {
        this.threshold = threshold;
        trees = new ArrayList<>();
    }

    public void addTree(Tree t) {
        trees.add(t);
    }

    public boolean pass(int[][] grayImage, int[][] squares, int i, int j, float scale) {
        float sum = 0;
        for (Tree t : trees) {
            sum += t.getVal(grayImage, squares, i, j, scale);
        }
        return sum > threshold;
    }

}
