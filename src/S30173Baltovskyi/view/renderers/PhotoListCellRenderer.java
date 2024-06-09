package S30173Baltovskyi.view.renderers;

import S30173Baltovskyi.controller.loaders.ImageLoader;
import S30173Baltovskyi.model.Photo;
import S30173Baltovskyi.view.components.MyLabel;

import javax.swing.*;
import java.awt.*;

public class PhotoListCellRenderer extends MyLabel implements ListCellRenderer<Photo> {
    private final ImageLoader imageLoader;

    public PhotoListCellRenderer(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Photo photo, int index, boolean isSelected, boolean cellHasFocus) {

        ImageIcon imageIcon = imageLoader.getImageIcon(photo);
        if (imageIcon == null) {
            setText("Loading...");
            setIcon(null);
        }
        else {
            setText(null);
            setIcon(imageIcon);
        }

        if (isSelected) {
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(100, 150, 250), 3),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
        } else {
            setBackground(new Color(45, 45, 48));
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            setOpaque(true);
        }
        return this;
    }
}