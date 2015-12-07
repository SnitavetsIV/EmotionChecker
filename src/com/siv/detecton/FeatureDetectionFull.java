package com.siv.detecton;

import com.siv.detecton.db.DatabaseFormatter;
import com.siv.entity.DimensionReducedObject;
import com.siv.entity.FacialImage;
import com.siv.entity.ImageConversionException;
import java.awt.image.BufferedImage;
import javax.media.jai.JAI;

/**
 *
 * @author Ilya Snitavets
 */
public class FeatureDetectionFull {

    public void detect(String m_imageFile) throws ImageConversionException {
        BufferedImage src
                = JAI.create("fileload", m_imageFile).getAsBufferedImage();

        FacialImage facialImage = new FacialImage(src);

        // Debug
        //facialImage.writeNext();
        DimensionReducedObject dimReduced
                = new DimensionReducedObject(facialImage, 0);

        DatabaseFormatter formatter
                = new DatabaseFormatter(0, dimReduced);

        System.out.println(formatter.databaseEncoding());

    }

}
