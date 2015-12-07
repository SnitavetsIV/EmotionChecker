package com.siv.detecton.db;

import com.siv.entity.DimensionReducedObject;
import java.util.Random;

public class DatabaseFormatter {

    String m_fileID;
    int m_emotionType;
    DimensionReducedObject m_dimReduced;

    public DatabaseFormatter(int emotionType, DimensionReducedObject dimReduced) {
        m_emotionType = emotionType;
        m_dimReduced = dimReduced;

        // Guarantee uniqueness by using system time and a random number.
        Random generator = new Random();
        int r = generator.nextInt();
        r = r < 0 ? -r : r;

        // Hash with a perfect key -- should be okay.
        m_fileID
                = Integer.toHexString(r)
                + Long.toHexString(System.currentTimeMillis());
    }

    public String fileID() {
        return m_fileID;
    }

    public String databaseEncoding() {
        return fileID() + "," + m_emotionType + "," + m_dimReduced.toString();
    }
}
