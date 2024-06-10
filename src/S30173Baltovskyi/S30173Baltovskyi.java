package S30173Baltovskyi;

import S30173Baltovskyi.controller.FullScreenPhotoController;
import S30173Baltovskyi.controller.loaders.ImageCollectionController;
import S30173Baltovskyi.controller.search.SearchPhotos;
import S30173Baltovskyi.view.panels.*;

import javax.swing.*;
import java.awt.*;

public class S30173Baltovskyi {

    private S30173Baltovskyi() {
        /*---------- Frame ----------*/
        JFrame frame = new JFrame("Photo Browser");

        /*---------- Panels ----------*/
        CentralPanel centralPanel = new CentralPanel();
        TopPanel topPanel = new TopPanel();
        LeftPanel leftPanel = new LeftPanel();
        RightPanel rightPanel = new RightPanel();
        new SplitPane(leftPanel, centralPanel, frame);

        // Search
        new SearchPhotos(topPanel, centralPanel, rightPanel);

        /*------------------------- Main Frame add -------------------------*/
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(rightPanel, BorderLayout.EAST);

        /*---------- Loaders ----------*/
        new FullScreenPhotoController(centralPanel, rightPanel);
        ImageCollectionController imageCollectionLoader = new ImageCollectionController(centralPanel, rightPanel, topPanel, leftPanel);

        // Initialize list
        imageCollectionLoader.initializeListModel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1020,800);
        frame.setMinimumSize(new Dimension(617,548));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new S30173Baltovskyi();
    }
}