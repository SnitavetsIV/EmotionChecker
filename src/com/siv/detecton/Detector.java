package com.siv.detecton;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.io.*;

import org.jdom.*;
import org.jdom.input.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Detector {

    List<Stage> stages;
    Point size;

    public Detector(String filename) throws java.io.FileNotFoundException {
        this(new FileInputStream(filename));
    }

    public Detector(InputStream input) {
        org.jdom.Document document = null;
        Element racine;
        stages = new LinkedList<>();
        SAXBuilder sxb = new SAXBuilder();
        try {
            document = sxb.build(input);
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
        racine = (Element) document.getRootElement().getChildren().get(0);
        Scanner scanner = new Scanner(racine.getChild("size").getText());
        size = new Point(scanner.nextInt(), scanner.nextInt());
        Iterator it = racine.getChild("stages").getChildren("_").iterator();
        while (it.hasNext()) {
            Element stage = (Element) it.next();
            float thres = Float.parseFloat(stage.getChild("stage_threshold").getText());
            //System.out.println(thres);
            Iterator it2 = stage.getChild("trees").getChildren("_").iterator();
            Stage st = new Stage(thres);
            while (it2.hasNext()) {
                Element tree = ((Element) it2.next());
                Tree t = new Tree();
                Iterator it4 = tree.getChildren("_").iterator();
                while (it4.hasNext()) {
                    Element feature = (Element) it4.next();
                    float thres2 = Float.parseFloat(feature.getChild("threshold").getText());
                    int left_node = -1;
                    float left_val = 0;
                    boolean has_left_val = false;
                    int right_node = -1;
                    float right_val = 0;
                    boolean has_right_val = false;
                    Element e;
                    if ((e = feature.getChild("left_val")) != null) {
                        left_val = Float.parseFloat(e.getText());
                        has_left_val = true;
                    } else {
                        left_node = Integer.parseInt(feature.getChild("left_node").getText());
                        has_left_val = false;
                    }

                    if ((e = feature.getChild("right_val")) != null) {
                        right_val = Float.parseFloat(e.getText());
                        has_right_val = true;
                    } else {
                        right_node = Integer.parseInt(feature.getChild("right_node").getText());
                        has_right_val = false;
                    }
                    Feature f = new Feature(thres2, left_val, left_node, has_left_val, right_val, right_node, has_right_val, size);
                    Iterator it3 = feature.getChild("feature").getChild("rects").getChildren("_").iterator();
                    while (it3.hasNext()) {
                        String s = ((Element) it3.next()).getText().trim();
                        Rect r = Rect.fromString(s);
                        f.add(r);
                    }

                    t.addFeature(f);
                }
                st.addTree(t);
            }
            stages.add(st);
        }
    }

    public List<java.awt.Rectangle> getFaces(int[][] array, float baseScale, float scale_inc, final float increment, int min_neighbors, boolean doCannyPruning) {
        if (array == null || array.length == 0) {
            return null;
        }
        final int h = array.length;
        final int w = array[0].length;
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                image.setRGB(x, y, array[y][x]);
            }
        }
        List<Rectangle> ret = new ArrayList<>();
        int width = image.getWidth();
        int height = image.getHeight();
        float maxScale = (Math.min((width + 0.f) / size.x, (height + 0.0f) / size.y));
        int[][] grayImage = new int[width][height];
        int[][] img = new int[width][height];
        int[][] squares = new int[width][height];
        for (int i = 0; i < width; i++) {
            int col = 0;
            int col2 = 0;
            for (int j = 0; j < height; j++) {
                int c = image.getRGB(i, j);
                int red = (c & 0x00ff0000) >> 16;
                int green = (c & 0x0000ff00) >> 8;
                int blue = c & 0x000000ff;
                int value = (30 * red + 59 * green + 11 * blue) / 100;
                img[i][j] = value;
                grayImage[i][j] = (i > 0 ? grayImage[i - 1][j] : 0) + col + value;
                squares[i][j] = (i > 0 ? squares[i - 1][j] : 0) + col2 + value * value;
                col += value;
                col2 += value * value;
            }
        }
        int[][] canny1 = null;
        if (doCannyPruning) {
            canny1 = getIntegralCanny(img);
        }
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (float scale1 = baseScale; scale1 < maxScale; scale1 *= scale_inc) {
            final float scale = scale1;
            final int[][] canny = canny1;
            service.submit(() -> {
                int step = (int) (scale * 24 * increment);
                int size1 = (int) (scale * 24);
                for (int i = 0; i < width - size1; i += step) {
                    for (int j = 0; j < height - size1; j += step) {
                        if (doCannyPruning) {
                            int edges_density = canny[i + size1][j + size1] + canny[i][j] - canny[i][j + size1] - canny[i + size1][j];
                            int d = edges_density / size1 / size1;
                            if (d < 20 || d > 100) {
                                continue;
                            }
                        }
                        boolean pass = true;
                        for (Stage s : stages) {
                            if (!s.pass(grayImage, squares, i, j, scale)) {
                                pass = false;
                                break;
                            }
                        }
                        if (pass) {
                            ret.add(new Rectangle(i, j, size1, size1));
                        }
                    }
                }
            });

        }
        service.shutdown();
        try {
            service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Logger.getLogger(Detector.class.getName()).log(Level.SEVERE, null, e);
        }
        return merge(ret, min_neighbors);
    }

    public int[][] getIntegralCanny(int[][] grayImage) {
        int[][] canny = new int[grayImage.length][grayImage[0].length];
        for (int i = 2; i < canny.length - 2; i++) {
            for (int j = 2; j < canny[0].length - 2; j++) {
                int sum = 0;
                sum += 2 * grayImage[i - 2][j - 2];
                sum += 4 * grayImage[i - 2][j - 1];
                sum += 5 * grayImage[i - 2][j + 0];
                sum += 4 * grayImage[i - 2][j + 1];
                sum += 2 * grayImage[i - 2][j + 2];
                sum += 4 * grayImage[i - 1][j - 2];
                sum += 9 * grayImage[i - 1][j - 1];
                sum += 12 * grayImage[i - 1][j + 0];
                sum += 9 * grayImage[i - 1][j + 1];
                sum += 4 * grayImage[i - 1][j + 2];
                sum += 5 * grayImage[i + 0][j - 2];
                sum += 12 * grayImage[i + 0][j - 1];
                sum += 15 * grayImage[i + 0][j + 0];
                sum += 12 * grayImage[i + 0][j + 1];
                sum += 5 * grayImage[i + 0][j + 2];
                sum += 4 * grayImage[i + 1][j - 2];
                sum += 9 * grayImage[i + 1][j - 1];
                sum += 12 * grayImage[i + 1][j + 0];
                sum += 9 * grayImage[i + 1][j + 1];
                sum += 4 * grayImage[i + 1][j + 2];
                sum += 2 * grayImage[i + 2][j - 2];
                sum += 4 * grayImage[i + 2][j - 1];
                sum += 5 * grayImage[i + 2][j + 0];
                sum += 4 * grayImage[i + 2][j + 1];
                sum += 2 * grayImage[i + 2][j + 2];

                canny[i][j] = sum / 159;
            }
        }
        int[][] grad = new int[grayImage.length][grayImage[0].length];
        for (int i = 1; i < canny.length - 1; i++) {
            for (int j = 1; j < canny[0].length - 1; j++) {
                int grad_x = -canny[i - 1][j - 1] + canny[i + 1][j - 1] - 2 * canny[i - 1][j] + 2 * canny[i + 1][j] - canny[i - 1][j + 1] + canny[i + 1][j + 1];
                int grad_y = canny[i - 1][j - 1] + 2 * canny[i][j - 1] + canny[i + 1][j - 1] - canny[i - 1][j + 1] - 2 * canny[i][j + 1] - canny[i + 1][j + 1];
                grad[i][j] = Math.abs(grad_x) + Math.abs(grad_y);
            }
        }
        for (int i = 0; i < canny.length; i++) {
            int col = 0;
            for (int j = 0; j < canny[0].length; j++) {
                int value = grad[i][j];
                canny[i][j] = (i > 0 ? canny[i - 1][j] : 0) + col + value;
                col += value;
            }
        }
        return canny;
    }

    public List<Rectangle> merge(List<Rectangle> rects, int min_neighbors) {
        List<Rectangle> retour = new ArrayList<>();
        int[] ret = new int[rects.size()];
        int nb_classes = 0;
        for (int i = 0; i < rects.size(); i++) {
            boolean found = false;
            for (int j = 0; j < i; j++) {
                if (equals(rects.get(j), rects.get(i))) {
                    found = true;
                    ret[i] = ret[j];
                }
            }
            if (!found) {
                ret[i] = nb_classes;
                nb_classes++;
            }
        }
        int[] neighbors = new int[nb_classes];
        Rectangle[] rect = new Rectangle[nb_classes];
        for (int i = 0; i < nb_classes; i++) {
            neighbors[i] = 0;
            rect[i] = new Rectangle(0, 0, 0, 0);
        }
        for (int i = 0; i < rects.size(); i++) {
            neighbors[ret[i]]++;
            rect[ret[i]].x += rects.get(i).x;
            rect[ret[i]].y += rects.get(i).y;
            rect[ret[i]].height += rects.get(i).height;
            rect[ret[i]].width += rects.get(i).width;
        }
        for (int i = 0; i < nb_classes; i++) {
            int n = neighbors[i];
            if (n >= min_neighbors) {
                Rectangle r = new Rectangle(0, 0, 0, 0);
                r.x = (rect[i].x * 2 + n) / (2 * n);
                r.y = (rect[i].y * 2 + n) / (2 * n);
                r.width = (rect[i].width * 2 + n) / (2 * n);
                r.height = (rect[i].height * 2 + n) / (2 * n);
                retour.add(r);
            }
        }
        return retour;
    }

    public boolean equals(Rectangle r1, Rectangle r2) {
        int distance = (int) (r1.width * 0.2);
        if (r2.x <= r1.x + distance
                && r2.x >= r1.x - distance
                && r2.y <= r1.y + distance
                && r2.y >= r1.y - distance
                && r2.width <= (int) (r1.width * 1.2)
                && (int) (r2.width * 1.2) >= r1.width) {
            return true;
        }
        return r1.x >= r2.x && r1.x + r1.width <= r2.x + r2.width && r1.y >= r2.y && r1.y + r1.height <= r2.y + r2.height;
    }
}
