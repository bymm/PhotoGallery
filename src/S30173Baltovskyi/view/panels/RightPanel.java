package S30173Baltovskyi.view.panels;

import S30173Baltovskyi.model.PhotoCollection;
import S30173Baltovskyi.model.PhotoCollectionListModel;
import S30173Baltovskyi.view.components.MyList;
import S30173Baltovskyi.view.components.MyScrollPane;
import S30173Baltovskyi.view.renderers.PhotoCollectionListCellRenderer;

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

    public MyList<PhotoCollection> getCollectionList() {
        return collectionList;
    }

    public PhotoCollectionListModel getCollectionListModel() {
        return collectionListModel;
    }
}