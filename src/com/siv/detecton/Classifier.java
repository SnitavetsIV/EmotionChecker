package com.siv.detecton;

import com.siv.entity.FaceType;
import com.siv.entity.IntegralImage;
import com.siv.entity.Rect;

/**
 *
 * @author Ilya
 */
public interface Classifier {

    FaceType classify(IntegralImage img, Rect windowRect);
}
