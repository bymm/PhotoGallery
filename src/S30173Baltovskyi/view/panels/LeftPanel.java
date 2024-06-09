package S30173Baltovskyi.view.panels;

import S30173Baltovskyi.model.Photo;
import S30173Baltovskyi.view.components.MyLabel;
import S30173Baltovskyi.view.components.MyPhotoInfoTextArea;
import S30173Baltovskyi.view.components.MyScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class LeftPanel extends MySidePanel {
    private MyScrollPane scrollPane;
    private JPanel contentPanel;
    private MyPhotoInfoTextArea titleInfo;
    private MyPhotoInfoTextArea tagsInfo;
    private MyPhotoInfoTextArea dateInfo;
    private MyPhotoInfoTextArea descriptionInfo;

    public LeftPanel() {
        super("Image Description:");
        initializeComponents();
    }

    private void initializeComponents() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 5, 15, 5));
        contentPanel.setBackground(new Color(45,45,48));

        MyLabel titleLabel = new MyLabel("Title:");
        titleInfo = new MyPhotoInfoTextArea("");
        MyLabel tagsLabel = new MyLabel("Tags:");
        tagsInfo = new MyPhotoInfoTextArea("");
        MyLabel dateLabel = new MyLabel("Date:");
        dateInfo = new MyPhotoInfoTextArea("");
        MyLabel descriptionLabel = new MyLabel("Description:");
        descriptionInfo = new MyPhotoInfoTextArea("");

        contentPanel.add(titleLabel);
        contentPanel.add(titleInfo);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(tagsLabel);
        contentPanel.add(tagsInfo);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(dateLabel);
        contentPanel.add(dateInfo);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(descriptionLabel);
        contentPanel.add(descriptionInfo);

        setComponentsVisible(false);

        scrollPane = new MyScrollPane(contentPanel);

        add(scrollPane);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                scrollPane.getViewport().setViewPosition(new Point(0, 0));
            }
        });
    }

    public void setComponentsVisible(boolean visible) {
        contentPanel.setVisible(visible);
    }

    public void setPhoto(Photo photo) {
        if (photo != null) {
            titleInfo.setText(photo.getTitle());
            tagsInfo.setText(photo.printTags().trim().isEmpty() ? "no tags" : photo.printTags());
            dateInfo.setText(photo.getDate().trim().isEmpty() ? "no date" : photo.getDate());
            descriptionInfo.setText(photo.getDescription().trim().isEmpty() ? "no description" : photo.getDescription());
            setComponentsVisible(true);
        } else
            setComponentsVisible(false);
    }
}
