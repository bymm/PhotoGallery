package S30173Baltovskyi.controller.listeners;

import S30173Baltovskyi.model.PhotoCollection;

@FunctionalInterface
public interface PhotoCollectionChangeListener {
    void onChangePhotoCollection(PhotoCollection photoCollection);
}