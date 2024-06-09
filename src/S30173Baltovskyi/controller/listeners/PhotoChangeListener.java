package S30173Baltovskyi.controller.listeners;

import S30173Baltovskyi.model.Photo;

@FunctionalInterface
public interface PhotoChangeListener {
    void onChangePhoto(Photo photo);
}
