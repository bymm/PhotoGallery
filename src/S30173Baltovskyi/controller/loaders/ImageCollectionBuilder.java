package S30173Baltovskyi.controller.loaders;

import S30173Baltovskyi.model.Photo;
import S30173Baltovskyi.model.PhotoListModel;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;

public class ImageCollectionBuilder extends SwingWorker<Void, Photo> {
    private final File[] files;
    private final PhotoListModel photoListModel;

    public ImageCollectionBuilder(File[] files, PhotoListModel photoListModel) {
        this.files = files;
        this.photoListModel = photoListModel;
    }

    @Override
    protected Void doInBackground() {
        File destDir = new File("rawPhotos", photoListModel.getCurrentCollection().getUuid());
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

                        // Create instance of a photo
                        Photo photo = new Photo("Untitled", new ArrayList<>(),
                                String.valueOf(LocalDate.now()), "", destFile.getPath());
                        photoListModel.getCurrentCollection().addPhoto(photo);
                    }
                } catch (IOException e) {
                    System.out.println("Error reading file: " + fileName);
                }
            }
        }
        photoListModel.setPhotoCollection(photoListModel.getCurrentCollection());
        return null;
    }

    private boolean isImageFile(String extension) {
        return extension.equals("png") || extension.equals("jpg") || extension.equals("jpeg") ||
                extension.equals("gif") || extension.equals("bmp") || extension.equals("wbmp");
    }
}