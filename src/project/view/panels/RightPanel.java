package project.view.panels;

import project.view.components.MyList;
import project.view.renderers.PhotoCollectionListCellRenderer;
import project.model.PhotoCollectionListModel;
import project.view.components.MyScrollPane;
import project.model.PhotoCollection;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends MySidePanel {
    private PhotoCollectionListModel collectionListModel;
    private MyList<PhotoCollection> collectionList;

    public RightPanel() {
        super("Collections:");
        initializeComponents();
    }

    private void initializeComponents() {
        collectionListModel = new PhotoCollectionListModel();
        collectionList = new MyList<>(collectionListModel);

        collectionList.setCellRenderer(new PhotoCollectionListCellRenderer());
        collectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        collectionList.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        collectionList.setFixedCellHeight(30);
        collectionList.setFixedCellWidth(186);
        collectionList.setFont(new Font("Arial", Font.PLAIN, 16));
        collectionList.setBackground(new Color(45,45,48));
        collectionList.setForeground(new Color(211, 211, 211));

        MyScrollPane collectionScroll = new MyScrollPane(collectionList);
        add(collectionScroll);
    }

    public JList<PhotoCollection> getCollectionList() {
        return collectionList;
    }

    public PhotoCollectionListModel getCollectionListModel() {
        return collectionListModel;
    }
}