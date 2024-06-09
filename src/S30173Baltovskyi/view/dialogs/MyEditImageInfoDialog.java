package S30173Baltovskyi.view.dialogs;

import S30173Baltovskyi.controller.loaders.ImageLoader;
import S30173Baltovskyi.model.Photo;
import S30173Baltovskyi.view.components.MyTextAreaNoWrap;
import S30173Baltovskyi.view.components.MyTextField;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class MyEditImageInfoDialog extends MyBaseDialog {
    private final ImageLoader imageLoader;
    private final Photo photo;
    private MyTextField titleTextField;
    private MyTextField tagsTextField;
    private MyTextField dateTextField;
    private MyTextField descriptionTextField;

    public MyEditImageInfoDialog(Component parentFrame, Photo photo, ImageLoader imageLoader) {
        super(parentFrame, "Edit Image Info", true);
        this.photo = photo;
        this.imageLoader = imageLoader;

        JPanel mainPanel = createMainPanel();
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        mainPanel.setBackground(new Color(37, 37, 38));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Icon
        ImageIcon icon = imageLoader.getImageIcon(photo);
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setPreferredSize(new Dimension(190, 190));
        gbc.gridwidth = 2;
        mainPanel.add(iconLabel, gbc);

        // Reset grid width for the next components
        gbc.gridwidth = 1;

        // Labels and TextFields: Title, Tags, Date, Description
        titleTextField = addLabelAndTextField(mainPanel, gbc, "Title:", photo.getTitle(), 1);
        tagsTextField = addLabelAndTextField(mainPanel, gbc, "Tags (#tag):", photo.printTags(), 2);
        dateTextField = addLabelAndTextField(mainPanel, gbc, "Date\n(yyyy-mm-dd):", photo.getDate(), 3);
        descriptionTextField = addLabelAndTextField(mainPanel, gbc, "Description:", photo.getDescription(), 4);

        return mainPanel;
    }

    private MyTextField addLabelAndTextField(JPanel panel, GridBagConstraints gbc, String labelText, String textFieldText, int gridy) {
        MyTextAreaNoWrap label = new MyTextAreaNoWrap(labelText);
        gbc.gridx = 0;
        gbc.gridy = gridy;
        panel.add(label, gbc);

        MyTextField textField = new MyTextField(textFieldText, new Dimension(250, 45), new Dimension(250, 45), false);
        textField.setRoundedCorners(true);
        textField.addActionListener(e -> action());

        gbc.gridx = 1;
        panel.add(textField, gbc);

        return textField;
    }

    protected void action() {
        String titleText = titleTextField.getText().trim();
        String tagsText = tagsTextField.getText().trim();
        String dateText = dateTextField.getText().trim();
        String descriptionText = descriptionTextField.getText().trim();

        if (!titleText.isEmpty()) {

            // Check if date can be parsed
            if (!dateText.isEmpty() && !isValidDate(dateText)) {
                MyBaseDialog.showMessageDialog(this, "Error", "Date cannot be parsed!");
                return;
            }

            // Ask for confirmation
            boolean confirmed = MyBaseDialog.showConfirmDialog(this, "Save Input Info to Image?");
            if (confirmed) {
                photo.setTitle(titleText);
                photo.setTags(tagsText);
                photo.setDate(dateText);
                photo.setDescription(descriptionText);
                dispose();
            }
        } else {
            titleTextField.wrongInputBorder();
            MyBaseDialog.showMessageDialog(this, "Error", "Title cannot be empty!");
        }
    }

    @Override
    protected void onOk() {
        action();
    }

    private boolean isValidDate(String dateText) {
        if (dateText.trim().isEmpty())
            return true;
        try {
            LocalDate.parse(dateText);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}