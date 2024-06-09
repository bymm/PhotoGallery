package project.view.components;

import javax.swing.*;
import java.awt.*;

public class MyTextAreaNoWrap extends JTextArea {
    public MyTextAreaNoWrap(String message) {
        super(message);
        setOpaque(false);
        setEditable(false);
        setFocusable(false);
        setFont(new Font("Arial", Font.BOLD, 16));
        setForeground(new Color(211, 211, 211));
    }
}
