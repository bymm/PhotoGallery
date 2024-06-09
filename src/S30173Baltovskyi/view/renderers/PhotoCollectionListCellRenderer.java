package S30173Baltovskyi.view.renderers;

import S30173Baltovskyi.model.PhotoCollection;

import javax.swing.*;
import java.awt.*;

public class PhotoCollectionListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel c = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value instanceof PhotoCollection photoCollection) {
            c.setText(photoCollection.getTitle());
            c.setIcon(null);
            c.setPreferredSize(new Dimension(186, 30));
        }

        if (isSelected) {
            c.setBackground(new Color(60, 63, 65));
            c.setForeground(Color.WHITE);
        } else {
            c.setBackground(new Color(45, 45, 48));
            c.setForeground(new Color(150, 150, 150));
        }

        return c;
    }
}

