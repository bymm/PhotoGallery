package project.controller.listeners;

import project.model.PhotoCollection;

@FunctionalInterface
public interface PhotoCollectionChangeListener {
    void onChangePhotoCollection(PhotoCollection photoCollection);
}