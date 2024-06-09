package S30173Baltovskyi.view.dialogs;

import S30173Baltovskyi.view.components.MyLabel;
import S30173Baltovskyi.view.components.MyTextField;

import javax.swing.*;
import java.awt.*;

public class MyInputDialog extends MyBaseDialog {
    private MyTextField textField;
    private String inputText;

    public MyInputDialog(Component parentFrame, String title, String labelText, String inputText) {
        super(parentFrame, title, true);
        this.inputText = inputText;
        JPanel topPanel = createTopPanel(labelText);
        add(topPanel, BorderLayout.CENTER);
    }

    private JPanel createTopPanel(String labelText) {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10,0));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        topPanel.setBackground(new Color(37,37,38));

        MyLabel label = new MyLabel(labelText);
        textField = new MyTextField(inputText, new Dimension(250, 45), new Dimension(250, 45), false);
        textField.setRoundedCorners(true);
        textField.addActionListener(e -> action());

        topPanel.add(label);
        topPanel.add(textField);

        return topPanel;
    }

    protected void action() {
        String text = textField.getText().trim();
        if (!text.isEmpty()) {
            inputText = text;
            dispose();
        } else {
            textField.wrongInputBorder();
            MyBaseDialog.showMessageDialog(this, "Error", "Input cannot be empty!");
        }
    }

    @Override
    protected void onOk() {
        action();
    }

    public String getInputText() {
        return inputText;
    }
}