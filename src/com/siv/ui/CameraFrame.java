package com.siv.ui;

import com.siv.detecton.ExecuteRecognitionApplication;
import com.siv.detecton.FormatResult;
import com.siv.detecton.NeuralNetworkTypeException;
import com.siv.detecton.db.DatabaseFormatter;
import com.siv.detecton.db.DatabaseParserException;
import com.siv.entity.DimensionReducedObject;
import com.siv.entity.FacialImage;
import com.siv.entity.ImageConversionException;
import com.siv.entity.MouthImage;
import com.siv.filter.ClarityFilter;
import com.siv.filter.FaceFilter;
import com.siv.filter.FilterType;
import com.siv.filter.GausFilter;
import com.siv.filter.GrayFilter;
import com.siv.filter.MedianFilter;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CameraFrame extends javax.swing.JFrame {

    private final VideoDisplayer displayer;

    public CameraFrame() {
        initComponents();
        displayer = new VideoDisplayer(jLabel1);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        faceButton = new javax.swing.JToggleButton();
        grayButton = new javax.swing.JToggleButton();
        jSpinner1 = new javax.swing.JSpinner();
        medianButton = new javax.swing.JToggleButton();
        videoButton = new javax.swing.JToggleButton();
        gausButton = new javax.swing.JToggleButton();
        clarityButton = new javax.swing.JToggleButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setMaximumSize(new java.awt.Dimension(640, 480));
        jPanel1.setMinimumSize(new java.awt.Dimension(640, 480));
        jPanel1.setPreferredSize(new java.awt.Dimension(640, 480));

        jLabel1.setMaximumSize(new java.awt.Dimension(640, 480));
        jLabel1.setMinimumSize(new java.awt.Dimension(640, 480));
        jLabel1.setPreferredSize(new java.awt.Dimension(640, 480));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        faceButton.setText("Face");
        faceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                faceButtonActionPerformed(evt);
            }
        });

        grayButton.setText("Gray");
        grayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grayButtonActionPerformed(evt);
            }
        });

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(3, 3, 11, 2));

        medianButton.setText("Median");
        medianButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                medianButtonActionPerformed(evt);
            }
        });

        videoButton.setText("video");
        videoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                videoButtonActionPerformed(evt);
            }
        });

        gausButton.setText("Gaus");
        gausButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gausButtonActionPerformed(evt);
            }
        });

        clarityButton.setText("Clarity");
        clarityButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clarityButtonActionPerformed(evt);
            }
        });

        jButton1.setText("Points");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Emotions");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(grayButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(medianButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(videoButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(faceButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(gausButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clarityButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(videoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(medianButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(gausButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clarityButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(grayButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(faceButton)
                .addContainerGap(171, Short.MAX_VALUE))
        );

        jLabel2.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(182, 182, 182)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void videoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_videoButtonActionPerformed
        if (videoButton.isSelected()) {
            displayer.startVideo();
        } else {
            displayer.stopVideo();
        }
    }//GEN-LAST:event_videoButtonActionPerformed

    private void medianButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medianButtonActionPerformed
        if (medianButton.isSelected()) {
            displayer.addFilter(new MedianFilter((int) jSpinner1.getValue()));
        } else {
            displayer.removeFilter(FilterType.MEDIAN);
        }
    }//GEN-LAST:event_medianButtonActionPerformed

    private void grayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grayButtonActionPerformed
        if (grayButton.isSelected()) {
            displayer.addFilter(new GrayFilter());
        } else {
            displayer.removeFilter(FilterType.GRAY);
        }
    }//GEN-LAST:event_grayButtonActionPerformed

    private void faceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_faceButtonActionPerformed
        if (faceButton.isSelected()) {
            displayer.addFilter(new FaceFilter());
        } else {
            displayer.removeFilter(FilterType.FACE_DETECTION);
        }
    }//GEN-LAST:event_faceButtonActionPerformed

    private void gausButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gausButtonActionPerformed
        if (gausButton.isSelected()) {
            displayer.addFilter(new GausFilter());
        } else {
            displayer.removeFilter(FilterType.GAUS);
        }
    }//GEN-LAST:event_gausButtonActionPerformed

    private void clarityButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clarityButtonActionPerformed
        if (clarityButton.isSelected()) {
            displayer.addFilter(new ClarityFilter());
        } else {
            displayer.removeFilter(FilterType.CLARITY);
        }
    }//GEN-LAST:event_clarityButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser fileopen = new JFileChooser();
        fileopen.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png"));
        int ret = fileopen.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
        try {
            File file = /*new File("H:\\Projects\\java-emotion-recognizer\\1.png");*/ fileopen.getSelectedFile();
            BufferedImage in = ImageIO.read(file);
            BufferedImage newImage = new BufferedImage(
                    in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);

            Graphics2D g = newImage.createGraphics();
            g.drawImage(in, 0, 0, null);
            g.dispose();
            FacialImage facialImage = new FacialImage(newImage);

            DimensionReducedObject dimReduced
                    = new DimensionReducedObject(facialImage, 0);

            jLabel1.setIcon(new ImageIcon(dimReduced.getImage()));
        } catch (IOException | ImageConversionException ex) {
            Logger.getLogger(CameraFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private static final int m_width = 25;
    private static final int m_height = 16;

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JFileChooser fileopen = new JFileChooser();
        fileopen.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png"));
        //int ret = fileopen.showDialog(null, "Открыть файл");
        if (0 == JFileChooser.APPROVE_OPTION || true) {

            try {
                BufferedImage src
                        = JAI.create("fileload", "H:\\Projects\\java-emotion-recognizer\\Images\\4Happy\\10.jpg").getAsBufferedImage();

                // Scale the image.
                BufferedImage scaled = new BufferedImage(
                        m_width, m_height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = scaled.createGraphics();
                graphics.setRenderingHint(
                        RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                graphics.drawImage(src, 0, 0, m_width, m_height, null);
                graphics.dispose();

                // Normalize.
                int[] rgb = new int[m_width * m_height];
                float[] normal = new float[m_width * m_height];
                scaled.getRGB(0, 0, m_width, m_height, rgb, 0, m_width);

                for (int y = 0; y < m_height; y++) {
                    for (int x = 0; x < m_width; x++) {
                        int index = y * m_width + x;

                        int R = (rgb[index] >> 16) & 0xff;
                        int G = (rgb[index] >> 8) & 0xff;
                        int B = rgb[index] & 0xff;

                        float intensity = ((R + G + B) / (255.0f * 3)) * 2.0f - 1.0f;
                        normal[index] = intensity;
                    }
                }

                // And write the result.
                DimensionReducedObject dimReduced
                        = new DimensionReducedObject(normal, 4);

                DatabaseFormatter formatter
                        = new DatabaseFormatter(0, dimReduced);

                try (FileWriter fw = new FileWriter(new File("temp.dat"))) {
                    fw.write(formatter.databaseEncoding());
                    fw.flush();
                }
                ExecuteRecognitionApplication sub = new ExecuteRecognitionApplication("backprop", "training.dat",
                        "temp.dat", 14, 400);
                sub.run();
                FormatResult r = sub.f;
                jLabel2.setText(r.toString());
                jLabel1.setIcon(new ImageIcon(src));
            } catch (IOException | NeuralNetworkTypeException | DatabaseParserException ex) {
                Logger.getLogger(CameraFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CameraFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CameraFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CameraFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CameraFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> {
            new CameraFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton clarityButton;
    private javax.swing.JToggleButton faceButton;
    private javax.swing.JToggleButton gausButton;
    private javax.swing.JToggleButton grayButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JToggleButton medianButton;
    private javax.swing.JToggleButton videoButton;
    // End of variables declaration//GEN-END:variables
}
