package S30173Baltovskyi.view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyButton extends JButton {

    public MyButton(String text) {
        super(text);
        initializeButton();
        addHoverEffect();
    }

    private void initializeButton() {
        setFont(new Font("Arial", Font.PLAIN, 18));
        setForeground(new Color(62, 62, 66));
        setPreferredSize(new Dimension(50,50));
    }

    private void addHoverEffect() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });
    }
}