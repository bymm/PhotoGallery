package S30173Baltovskyi.model;

import javax.swing.*;

public class PhotoListModel extends AbstractListModel<Photo> {
    private PhotoCollection currentCollection;

    public PhotoCollection getCurrentCollection() {
        return currentCollection;
    }

    public void setPhotoCollection(PhotoCollection collection) {
        currentCollection = collection;
        fireContentsChanged(this, 0, getSize()-1);
    }

    @Override
    public int getSize() {
        return currentCollection == null ? 0 : currentCollection.getPhotos().size();
    }

    @Override
    public Photo getElementAt(int index) {
        return currentCollection.getPhotos().get(index);
    }

    public void updatePhotos() {
        fireContentsChanged(this, 0, getSize()-1);
    }
}