package project.view.components;

import javax.swing.*;
import java.awt.*;

public class MyLabel extends JLabel {

    public MyLabel(String text) {
        super(text);
        setFont(new Font("Arial", Font.BOLD, 16));
        setForeground(new Color(211, 211, 211));
    }
}