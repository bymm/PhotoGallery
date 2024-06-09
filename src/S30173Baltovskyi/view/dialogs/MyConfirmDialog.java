package S30173Baltovskyi.view.dialogs;

import S30173Baltovskyi.view.components.MyTextAreaNoWrap;

import javax.swing.*;
import java.awt.*;

public class MyConfirmDialog extends MyBaseDialog {
    private boolean confirmed = false;

    public MyConfirmDialog(Component parentFrame, String message) {
        super(parentFrame, "Select an Option", true);
        JPanel messagePanel = createMessagePanel(message);
        add(messagePanel, BorderLayout.CENTER);
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

    @Override
    protected void onOk() {
        confirmed = true;
        dispose();
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}