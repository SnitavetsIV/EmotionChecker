package com.siv.entity;

/**
 *
 * @author Ilya
 */
public class Point {

    private final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double distWith(Point another) {
        return Math.sqrt(Math.pow(another.x - x, 2.0d) + Math.pow(another.y - y, 2.0d));
    }
}
