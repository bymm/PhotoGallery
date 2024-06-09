package S30173Baltovskyi.view.dialogs;

import S30173Baltovskyi.controller.loaders.ImageLoader;
import S30173Baltovskyi.model.Photo;
import S30173Baltovskyi.view.components.MyButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MyBaseDialog extends JDialog {
    protected JPanel buttonsPanel;

    public MyBaseDialog(Component parentFrame, String title, boolean modal) {
        super((Frame) SwingUtilities.getWindowAncestor(parentFrame), title, modal);
        initialize();
    }

    private void initialize() {
        createButtonsPanel();
        add(buttonsPanel, BorderLayout.SOUTH);
        setupKeys();
    }

    private void createButtonsPanel() {
        buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setBackground(new Color(45, 45, 48));

        JButton cancelButton = new MyButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(80, 40));
        cancelButton.addActionListener(e -> onCancel());

        JButton okButton = new MyButton("OK");
        okButton.setPreferredSize(new Dimension(50, 40));
        okButton.addActionListener(e -> onOk());

        buttonsPanel.add(cancelButton);
        buttonsPanel.add(okButton);
    }

    private void setupKeys() {
        JRootPane rootPane = getRootPane();
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = rootPane.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "ESCAPE_KEY");
        actionMap.put("ESCAPE_KEY", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "ENTER_KEY");
        actionMap.put("ENTER_KEY", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOk();
            }
        });
    }

    protected void onOk() {
        dispose();
    }

    protected void onCancel() {
        dispose();
    }


    public static String showInputDialog(Component parentFrame, String title, String labelText, String inputText) {
        MyInputDialog dialog = new MyInputDialog(parentFrame, title, labelText, inputText);

        dialog.pack();
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setResizable(false);
        dialog.setVisible(true);

        return dialog.getInputText();
    }

    public static boolean showConfirmDialog(Component parentFrame, String message) {
        MyConfirmDialog dialog = new MyConfirmDialog(parentFrame, message);

        dialog.pack();
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setResizable(false);
        dialog.setVisible(true);

        return dialog.isConfirmed();
    }

    public static void showMessageDialog(Component parentFrame, String title, String message) {
        MyMessageDialog dialog = new MyMessageDialog(parentFrame, title, message);

        dialog.pack();
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }

    public static void showEditImageInfoDialog(Component parentFrame, Photo photo, ImageLoader imageLoader) {
        MyEditImageInfoDialog dialog = new MyEditImageInfoDialog(parentFrame, photo, imageLoader);

        dialog.pack();
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }
}