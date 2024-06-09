package project.controller.workers;

import project.view.panels.CentralPanel;
import project.model.Photo;
import project.model.PhotoCollection;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ImageLoaderWorker extends SwingWorker<Void, Photo> {
    private final File[] files;
    private final PhotoCollection selectedCollection;
    private final CentralPanel centralPanel;

    public ImageLoaderWorker(File[] files, PhotoCollection selectedCollection, CentralPanel centralPanel) {
        this.files = files;
        this.selectedCollection = selectedCollection;
        this.centralPanel = centralPanel;
    }

    @Override
    protected Void doInBackground() throws Exception {
        File destDir = new File("rawPhotos", selectedCollection.getUuid());
        if (!destDir.exists()) destDir.mkdirs();

        for (File file : files) {
            String fileName = file.getName();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

            if (isImageFile(fileExtension)) {
                try {
                    // Copy file to directory
                    File destFile = new File(destDir, fileName);

                    if (!destFile.exists()) {
                        Files.copy(file.toPath(), destFile.toPath());

                        // Create instance of a photo, publish
                        Photo photo = new Photo("Untitled", new ArrayList<>(),
                                String.valueOf(LocalDate.now()), "", destFile.getPath());
                        publish(photo);
                    }
                } catch (IOException e) {
                    System.out.println("Error reading file: " + fileName);
                }
            }
        }
        return null;
    }

    @Override
    protected void process(List<Photo> photos) {
        for (Photo photo : photos)
            new ImageBufferWorker(photo, 190, 190, selectedCollection, centralPanel).execute();
    }

    private boolean isImageFile(String extension) {
        return extension.equals("png") || extension.equals("jpg") || extension.equals("jpeg") ||
                extension.equals("gif") || extension.equals("bmp") || extension.equals("wbmp");
    }
}