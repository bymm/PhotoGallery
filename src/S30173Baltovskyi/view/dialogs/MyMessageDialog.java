package project.view.dialogs;

import project.view.components.MyButton;
import project.view.components.MyTextAreaNoWrap;

import javax.swing.*;
import java.awt.*;

public class MyMessageDialog extends MyBaseDialog {
    public MyMessageDialog(Component parentFrame, String title, String message) {
        super(parentFrame, title, true);
        JPanel messagePanel = createMessagePanel(message);
        add(messagePanel, BorderLayout.CENTER);

        customizeButtonsPanel();
    }

    private JPanel createMessagePanel(String message) {
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        messagePanel.setBackground(new Color(37,37,38));

        JTextArea textArea = new MyTextAreaNoWrap(message);
        messagePanel.add(textArea);

        return messagePanel;
    }

    private void customizeButtonsPanel() {
        buttonsPanel.removeAll();

        JButton okButton = new MyButton("OK");
        okButton.setPreferredSize(new Dimension(50, 40));
        okButton.addActionListener(e -> onOk());

        buttonsPanel.add(okButton);
    }
}