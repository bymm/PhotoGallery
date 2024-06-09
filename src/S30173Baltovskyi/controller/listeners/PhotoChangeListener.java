package project.controller.listeners;

import project.model.Photo;

@FunctionalInterface
public interface PhotoChangeListener {
    void onChangePhoto(Photo photo);
}
