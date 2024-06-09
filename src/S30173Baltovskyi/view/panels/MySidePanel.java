package project.view.panels;

import project.view.components.MyLabel;

import javax.swing.*;
import java.awt.*;

public class MySidePanel extends JPanel {

    public MySidePanel(String text) {
        setLayout(new BorderLayout());

        JLabel topLabel = new MyLabel(text);
        topLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        add(topLabel, BorderLayout.NORTH);

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setBackground(new Color(37, 37, 38));
    }
}