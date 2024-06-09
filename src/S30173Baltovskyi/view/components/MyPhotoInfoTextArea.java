package S30173Baltovskyi.view.components;

import javax.swing.*;
import java.awt.*;

public class MyPhotoInfoTextArea extends JTextArea {
    public MyPhotoInfoTextArea(String text) {
        super(text);
        setFont(new Font("Arial", Font.PLAIN, 14));
        setForeground(new Color(200, 200, 200));
        setBackground(new Color(0,0,0,0));
        //setMinimumSize(new Dimension(0, 0));
        setOpaque(false);
        setLineWrap(true);
        setWrapStyleWord(false);
        setEditable(false);
        setFocusable(false);
        setAlignmentX(Component.LEFT_ALIGNMENT);
    }
}