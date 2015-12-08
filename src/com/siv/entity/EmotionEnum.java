package com.siv.entity;

/**
 *
 * @author Ilya Snitavets
 */
public enum EmotionEnum {

    Anger(0),
    Disgust(2),
    Fear(3),
    Happiness(4),
    Sadness(6),
    Surprise(7);

    private final int num;

    private EmotionEnum(int i) {
        num = i;
    }

    public int getNum() {
        return num;
    }
}
