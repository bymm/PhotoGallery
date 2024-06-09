package project.controller.workers;

import project.view.panels.CentralPanel;
import project.model.PhotoCollection;
import project.model.Photo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class ImageBufferWorker extends SwingWorker<Void, BufferedImage> {
    private final Photo photo;
    private int width;
    private int height;

    private PhotoCollection selectedCollection;
    private CentralPanel centralPanel;

    public ImageBufferWorker(Photo photo, int width, int height, PhotoCollection selectedCollection, CentralPanel centralPanel) {
        this.photo = photo;
        this.width = width;
        this.height = height;
        this.selectedCollection = selectedCollection;
        this.centralPanel = centralPanel;
    }

    public ImageBufferWorker(Photo photo, int width, int height) {
        this.photo = photo;
        this.width = width;
        this.height = height;
    }

    public ImageBufferWorker(Photo photo) {
        this.photo = photo;
    }

    @Override
    protected Void doInBackground() throws Exception {
        try {
            // Read image from file path, publish again
            BufferedImage bufferedImage = ImageIO.read(new File(photo.getImagePath()));
            if (bufferedImage != null) {
                photo.setOriginalWidth(bufferedImage.getWidth());
                photo.setOriginalHeight(bufferedImage.getHeight());
                publish(bufferedImage);
            }
        } catch (Exception e) {
            System.out.println("Error while loading image: " + photo.getImagePath());
        }
        return null;
    }

    @Override
    public void process(List<BufferedImage> images) {
        for (BufferedImage image : images) {
            if (selectedCollection != null && centralPanel != null) {
                new ImageResizerWorker(photo, image, width, height, selectedCollection, centralPanel).execute();
            } else if (width > 0 && height > 0){
                new ImageResizerWorker(photo, image, width, height).execute();
            } else {
                System.out.println("Just Photo");
            }
        }
    }
}