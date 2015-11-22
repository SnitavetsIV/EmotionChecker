package com.siv.detecton;

import com.siv.entity.IntegralImage;
import com.siv.entity.Rect;

/**
 *
 * @author Ilya
 */
public class FeatureB extends Feature {

    public FeatureB(Rect shape) {
        super(shape);
        type = 'B';
    }

    @Override
    public int getValue(IntegralImage img, Rect workingRect) {
        int x = workingRect.getX(), y = workingRect.getY(),
                w = workingRect.getWidth(), h = workingRect.getHeight();
        Rect workingRect1 = new Rect(x, y + h, w, h);
        Rect workingRect2 = new Rect(x, y, w, h);
        return img.sumValue(workingRect1) - img.sumValue(workingRect2);
    }

}
