package com.siv.entity;

import Jama.Matrix;
import com.siv.filter.GaborFilter;
import pca_transform.PCA;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class DimensionReducedObject {

    // The number of gabor samples to save to file.
    public static final int resizeX = 10;
    public static final int resizeY = 10;

    // Be careful when changing this. Have to updates thetas array below as
    // well.
    public static final int numGaborImages = 6;
    public static int numGaborSamples
            = resizeX * resizeY * numGaborImages * numGaborImages;

    // Reduce the dimensionality to this value. Note that this is the maximum
    // value for the number of features. In the case when the input
    // dimensionality is already lower than this, no PCA is performed. When PCA
    // *is* performed, if the number of dimensions required to account for
    // ~100% of the variance is still less than this value, take the lower
    // value. This simply serves as the max.
    protected int m_numFeatures;

    protected float[] m_array;
    protected float[] m_reduced;

    private BufferedImage image;

    public DimensionReducedObject(FacialImage facialImage, int numFeatures) {
        m_reduced = null;
        m_numFeatures = numFeatures;

        BufferedImage src = facialImage.getGreyImage1();

        Vector<BufferedImage> bvec = new Vector<>();
        double thetas[] = new double[]{
            Math.PI / 8, Math.PI / 4, 3 * Math.PI / 8, Math.PI / 2, 5 * Math.PI / 8,
            3 * Math.PI / 4
        };
        for (int i = 0; i < numGaborImages; i++) {
            int exp = 1;
            for (int j = 0; j < numGaborImages; j++) {
                BufferedImage b = new BufferedImage(src.getWidth(),
                        src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

                exp *= 2;
                double k = 2 * Math.PI / src.getWidth() * exp;
                GaborFilter g = new GaborFilter(k,
                        new double[]{k * Math.sin(thetas[i]), k * Math.cos(thetas[i])});

                g.filter(src, b);
                bvec.add(b);
            }
        }

        // Form an array from the down-sampled images.
        m_array = new float[numGaborSamples];
        int arrayCounter = 0;
        BufferedImage image1 = facialImage.getOut();
        for (int i = 0; i < numGaborImages * numGaborImages; i++) {
            BufferedImage scaled = new BufferedImage(
                    resizeX, resizeY, BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D graphics = scaled.createGraphics();
            graphics.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            graphics.drawImage(bvec.get(i), 0, 0, resizeX, resizeY, null);
            graphics.dispose();

            int[] rgb = new int[scaled.getWidth() * scaled.getHeight()];
            scaled.getRGB(0, 0, scaled.getWidth(), scaled.getHeight(), rgb,
                    0, scaled.getWidth());

            for (int j = 0; j < resizeX; j++) {
                for (int k = 0; k < resizeY; k++) {
                    int index = k * resizeX + j;
                    float val = (rgb[index] & 0x000000ff) / 1.0f;
                    m_array[arrayCounter] = val;
                    arrayCounter++;
                }
            }
        }

        BufferedImage src1 = facialImage.getImage();
        for (int i = 0; i < numGaborImages * numGaborImages; i++) {
            for (int x = 0; x < bvec.get(i).getWidth() - 13; x+=4) {
                for (int y = 0; y < bvec.get(i).getHeight() - 13; y+=4) {

                    int max = 0;
                    int xxx = 0;
                    int yyy = 0;

                    for (int xx = 0; xx < 13; xx++) {
                        for (int yy = 0; yy < 13; yy++) {
                            int rgb = bvec.get(i).getRGB(x + xx, y + yy);
                            if ((rgb & 0x000000ff) > max) {
                                max = rgb & 0x000000ff;
                                xxx = x + xx;
                                yyy = y + yy;
                            }
                        }
                    }

                    //if (max > 200) {
                        src1.setRGB(xxx, yyy, Color.RED.getRGB());
                   // }
                }
            }
        }
        image = src1;
    }

    public DimensionReducedObject(float[] array, int numFeatures) {
        m_reduced = null;
        m_numFeatures = numFeatures;
        m_array = array;
    }

    // Populate the reduced array by performing PCA on the objects... Returns
    // the number of features.
    //
    // Assume all dro's have the same length. If the first is long and the
    // second is short, we'll get a null pointer exception. Unhandled error case
    // because it'll only fail when the developer creates a bad file. The php
    // will normally manage the length.
    //
    // Also normalizes the data.
    public static int prinCompAnalysis(Vector<DimensionReducedObject> vec) {
        if (vec.isEmpty()) {
            return 0;
        }

        int length = vec.get(0).getArray().length;
        int numFeatures = vec.get(0).m_numFeatures;

        if (length <= numFeatures) {
            // No PCA is required. Just use the large array as the reduced
            // array.
            return length;
        }

        double[][] data = new double[vec.size()][length];
        for (int i = 0; i < vec.size(); i++) {
            float[] subdata = vec.get(i).getArray();
            for (int j = 0; j < length; j++) {
                data[i][j] = (double) subdata[j];
            }
        }

        Matrix trainingData = new Matrix(data);
        PCA pca = new PCA(trainingData);
        Matrix testingData = new Matrix(data);
        Matrix transformedData = pca.transform(testingData,
                PCA.TransformationType.WHITENING);

        // If the PCA has too many features, limit the copy.
        int actualFeatures = transformedData.getColumnDimension();
        actualFeatures = Math.min(actualFeatures, numFeatures);

        float max = 0.0f;
        float min = 0.0f;

        for (int i = 0; i < vec.size(); i++) {
            DimensionReducedObject dro = vec.get(i);
            dro.m_reduced = new float[actualFeatures];

            for (int j = 0; j < actualFeatures; j++) {
                dro.m_reduced[j] = (float) transformedData.get(i, j);

                if (dro.m_reduced[j] > max) {
                    max = dro.m_reduced[j];
                }
                if (dro.m_reduced[j] < min) {
                    min = dro.m_reduced[j];
                }
            }
        }

        // Normalize all data between -1.0 and 1.0.
        float absmax = Math.abs(min) + max;
        float frac = 2.0f / absmax;

        for (int i = 0; i < vec.size(); i++) {
            DimensionReducedObject dro = vec.get(i);

            for (int j = 0; j < actualFeatures; j++) {
                dro.m_reduced[j] = ((dro.m_reduced[j] + Math.abs(min)) * frac) - 1;
            }
        }

        return actualFeatures;
    }

    // Used for output to the file. Do not change to use the reduced array, that
    // would not be correct.
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < m_array.length; i++) {
            result.append(m_array[i]);

            if (i + 1 != m_array.length) {
                result.append(",");
            }
        }

        return result.toString();
    }

    // The input array, or the gabor samples.
    public float[] getArray() {
        return m_array;
    }

    // Reduced array, created as a result of PCA. Will return the large array
    // until PCA is done by the below static.
    public float[] getReducedArray() {
        return (m_reduced == null) ? m_array : m_reduced;
    }

    public BufferedImage getImage() {
        return image;
    }
}
