package com.siv.ui;

import com.siv.filter.FilterManager;
import com.siv.filter.FilterType;
import java.awt.image.BufferedImage;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.VideoInputFrameGrabber;

public class VideoDisplayer {

    private JLabel videoOut;
    private VideoThread videoThread;
    private final Java2DFrameConverter CONVERTER = new Java2DFrameConverter();
    private final FilterManager filterManager;
    
    public VideoDisplayer(JLabel videoOut) {
        filterManager = new FilterManager();
        this.videoOut = videoOut;
    }

    public JLabel getVideoOut() {
        return videoOut;
    }

    public void setVideoOut(JLabel videoOut) {
        this.videoOut = videoOut;
    }

    public void startVideo() {
        videoThread = new VideoThread();
        videoThread.start();
    }

    public void stopVideo() {
        if (videoThread != null) {
            videoThread.stopVideo();
        }
    }

    private class VideoThread extends Thread {

        private boolean work = true;

        @Override
        public void run() {
            FrameGrabber grabber = new VideoInputFrameGrabber(0);

            try {
                grabber.start();
                Frame img;
                while (work) {
                    img = grabber.grab();
                    if (img != null) {
                        BufferedImage image = CONVERTER.convert(img);
                        image = filterManager.apply(image);
                        videoOut.setIcon(new ImageIcon(image));
                    }
                }

            } catch (FrameGrabber.Exception ex) {
                Logger.getLogger(CameraFrame.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    grabber.stop();
                } catch (FrameGrabber.Exception ex) {
                    Logger.getLogger(VideoDisplayer.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

        public void stopVideo() {
            work = false;
        }

    }

    public void addFilter(FilterType type) {
        filterManager.addFilter(type);
    }

    public void removeFilter(FilterType type) {
        filterManager.removeFilter(type);
    }
}
