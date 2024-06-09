package S30173Baltovskyi.view.components;

import javax.swing.*;
import java.awt.*;

public class MyLabel extends JLabel {

    public MyLabel() {
        initialize();
    }

    public MyLabel(String text) {
        super(text);
        initialize();
    }

    private void initialize() {
        setFont(new Font("Arial", Font.BOLD, 16));
        setForeground(new Color(211, 211, 211));
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
    }
}