package project.view.panels;

import javax.swing.*;
import java.awt.*;

public class SplitPane extends JSplitPane {
    public SplitPane(LeftPanel leftPanel, CentralPanel centralPanel) {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, centralPanel);
        splitPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerSize(7);
        splitPane.setDividerLocation(200);
        splitPane.setBackground(new Color(100, 100, 100));
    }
}
