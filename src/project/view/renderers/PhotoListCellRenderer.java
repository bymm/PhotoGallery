package project.view.renderers;

import project.model.Photo;

import javax.swing.*;
import java.awt.*;

public class PhotoListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel c = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value instanceof Photo photo) {
            c.setText(null);
            c.setPreferredSize(new Dimension(200, 200));
            c.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            c.setIcon(photo.resizeImageIcon(190,190));
        }

        if (isSelected) {
            c.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(100, 150, 250), 3),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
        } else {
            c.setBackground(new Color(45, 45, 48));
            c.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            c.setOpaque(true);
        }
        return c;
    }
}