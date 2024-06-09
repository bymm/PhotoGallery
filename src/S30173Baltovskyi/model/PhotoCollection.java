package S30173Baltovskyi.model;

import S30173Baltovskyi.controller.listeners.PhotoChangeListener;
import S30173Baltovskyi.controller.listeners.PhotoCollectionChangeListener;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;

public class PhotoCollection implements Serializable, PhotoChangeListener {
    @Serial
    private static final long serialVersionUID = 1L;

    private String title;
    private final ArrayList<Photo> photos;
    private final String uuid;
    private transient PhotoCollectionChangeListener collectionChangeListener;

    public PhotoCollection(String title) {
        this.title = title;
        this.photos = new ArrayList<>();
        this.uuid = String.valueOf(UUID.randomUUID());
    }

    public void setCollectionChangeListener(PhotoCollectionChangeListener collectionChangeListener) {
        this.collectionChangeListener = collectionChangeListener;
    }

    @Override
    public void onChangePhoto(Photo photo) {
        notifyCollectionChangeListener();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
        this.title = newTitle;
       notifyCollectionChangeListener();
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public String getUuid() {
        return uuid;
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
        photo.setPhotoChangeListener(this);
        notifyCollectionChangeListener();
    }

    public void removePhotoAt(int index) {
        try {
            Path imagePath = Paths.get(photos.get(index).getImagePath());
            Files.deleteIfExists(imagePath);
            photos.remove(index);
            notifyCollectionChangeListener();
        } catch (IOException e) {
            System.out.println("Error removing photo at index " + index);
        }
    }

    public void clearCache() {
        photos.clear();
    }

    private void notifyCollectionChangeListener() {
        if (collectionChangeListener != null)
            collectionChangeListener.onChangePhotoCollection(this);
    }
}