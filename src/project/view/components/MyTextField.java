package project.view.components;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class MyTextField extends JTextField {
    private final String placeholderText;
    private boolean roundedCorners;
    private boolean addPlaceHolderFocusListener;

    public MyTextField(String text, Dimension prefSize, Dimension maxSize, boolean addPlaceHolderFocusListener) {
        super(text);
        this.placeholderText = text;
        this.roundedCorners = false;

        initializeTextField(prefSize, maxSize);
        customizeAppearance();
        if (addPlaceHolderFocusListener) addPlaceholderFocusListener();
    }

    private void initializeTextField(Dimension prefSize, Dimension maxSize) {
        setPreferredSize(prefSize);
        setMaximumSize(maxSize);
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    }

    private void customizeAppearance() {
        setFont(new Font("Arial", Font.PLAIN, 16));
        setForeground(Color.LIGHT_GRAY);
        setCaretColor(new Color(211, 211, 211));
        setBackground(new Color(45, 45, 48));
    }

    private void addPlaceholderFocusListener() {
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(placeholderText)) {
                    setText("");
                    setForeground(new Color(211, 211, 211));
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(placeholderText);
                    setForeground(Color.LIGHT_GRAY);
                }
            }
        });
    }

    public void wrongInputBorder() {
        Border paddingBorder = BorderFactory.createEmptyBorder(0, 10, 0, 10);
        setBorder(BorderFactory.createCompoundBorder(new RoundedBorder(15, Color.RED, 1), paddingBorder));
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (roundedCorners) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Paint background
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

            // Paint border if needed
            g2.setColor(getForeground());
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);

            // Paint the text field's content
            super.paintComponent(g);
        } else {
            super.paintComponent(g);
        }
    }

    @Override
    public void setOpaque(boolean isOpaque) {
        super.setOpaque(!roundedCorners);
    }

    public void setRoundedCorners(boolean roundedCorners) {
        this.roundedCorners = roundedCorners;
        setOpaque(!roundedCorners);
        repaint();
    }
}