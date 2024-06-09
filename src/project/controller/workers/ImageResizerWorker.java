package project.controller.workers;

import project.view.panels.CentralPanel;
import project.model.PhotoCollection;
import project.model.Photo;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageResizerWorker extends SwingWorker<Void, Void> {
    private final Photo photo;
    private final BufferedImage image;
    private final int width;
    private final int height;

    private PhotoCollection selectedCollection;
    private CentralPanel centralPanel;

    public ImageResizerWorker(Photo photo, BufferedImage image, int width, int height, PhotoCollection selectedCollection, CentralPanel centralPanel) {
        this.photo = photo;
        this.image = image;
        this.width = width;
        this.height = height;
        this.selectedCollection = selectedCollection;
        this.centralPanel = centralPanel;
    }

    public ImageResizerWorker(Photo photo, BufferedImage image, int width, int height) {
        this.photo = photo;
        this.image = image;
        this.width = width;
        this.height = height;
    }

    @Override
    protected Void doInBackground() throws Exception {
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        photo.putResizedIcon(new Dimension(width, height), resizedIcon);
        return null;
    }

    @Override
    protected void done() {
        if (selectedCollection != null && centralPanel != null) {
            // Add photo to collection, update the view
            selectedCollection.addPhoto(photo);
            centralPanel.getCurColNumPhotos().setText(Integer.toString(selectedCollection.getPhotos().size()));
            centralPanel.getPhotoListModel().updatePhotos();
        }
    }
}