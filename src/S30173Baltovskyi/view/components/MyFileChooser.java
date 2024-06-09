package project.view.components;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.util.Arrays;

public class MyFileChooser extends JFileChooser {
    public MyFileChooser() {
        setDialogTitle("Select Images");
        setMultiSelectionEnabled(true);
        setFileFilter(new FileNameExtensionFilter(
                ".png, .jpg, .jpeg, .gif, .bmp, .wbmp",
                "png", "jpg", "jpeg", "gif", "bmp", "wbmp")
        );

        // Apply custom UI settings before creating the file chooser
        UIManager.put("FileChooser.background", new Color(37, 37, 38));
        UIManager.put("FileChooser.foreground", new Color(211, 211, 211));
        UIManager.put("FileChooser.listViewBackground", new Color(37, 37, 38));
        UIManager.put("FileChooser.listViewForeground", new Color(211, 211, 211));
        UIManager.put("FileChooser.selectionBackground", new Color(45, 45, 48));
        UIManager.put("FileChooser.selectionForeground", new Color(255, 255, 255));
        UIManager.put("FileChooser.controlButtonBackground", new Color(45, 45, 48));
        UIManager.put("FileChooser.controlButtonForeground", new Color(255, 255, 255));

        // Apply the custom UI settings
        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();

        // Set background and foreground colors for components within JFileChooser
        setBackground(new Color(37, 37, 38));
        setForeground(new Color(211, 211, 211));

        Component[] components = getComponents(this);
        for (Component component : components) {
            if (component instanceof JPanel) {
                component.setBackground(new Color(37, 37, 38));
            } else if (component instanceof JLabel) {
                component.setForeground(new Color(211, 211, 211));
            } else if (component instanceof JButton) {
                component.setBackground(new Color(45, 45, 48));
                component.setForeground(new Color(255, 255, 255));
            } else if (component instanceof JList) {
                component.setBackground(new Color(37, 37, 38));
                component.setForeground(new Color(211, 211, 211));
                //component.setSelectionBackground(new Color(45, 45, 48));
                //component.setSelectionForeground(new Color(255, 255, 255));
            } else if (component instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) component;
                JViewport viewport = scrollPane.getViewport();
                viewport.setBackground(new Color(37, 37, 38));
                JList<?> list = (JList<?>) viewport.getView();
                list.setBackground(new Color(37, 37, 38));
                list.setForeground(new Color(211, 211, 211));
                list.setSelectionBackground(new Color(45, 45, 48));
                list.setSelectionForeground(new Color(255, 255, 255));
            }
        }
    }

    private Component[] getComponents(Container container) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof Container) {
                Component[] childComponents = getComponents((Container) component);
                components = concat(components, childComponents);
            }
        }
        return components;
    }

    private Component[] concat(Component[] first, Component[] second) {
        Component[] result = new Component[first.length + second.length];
        System.arraycopy(first, 0, result, 0, first.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("MyFileChooser Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLayout(new BorderLayout());

            MyFileChooser fileChooser = new MyFileChooser();
            frame.add(fileChooser, BorderLayout.CENTER);

            JButton openButton = new JButton("Open File Chooser");
            openButton.addActionListener(e -> {
                int returnValue = fileChooser.showOpenDialog(frame);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    System.out.println("Selected files: " + Arrays.toString(fileChooser.getSelectedFiles()));
                }
            });
            frame.add(openButton, BorderLayout.SOUTH);

            frame.setVisible(true);
        });
    }
}