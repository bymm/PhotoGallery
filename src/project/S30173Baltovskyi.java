package project;

import project.controller.loaders.ImageCollectionLoader;
import project.controller.photoprocessors.FullScreenPhotoController;
import project.view.panels.*;

import javax.swing.*;
import java.awt.*;

public class S30173Baltovskyi {

    public static void main(String[] args) {
        /*---------- Frame ----------*/
        JFrame frame = new JFrame("Photo Browser");


        /*---------- Panels ----------*/
        CentralPanel centralPanel = new CentralPanel();
        TopPanel topPanel = new TopPanel();
        LeftPanel leftPanel = new LeftPanel();
        RightPanel rightPanel = new RightPanel();
        SplitPane splitPane = new SplitPane(leftPanel, centralPanel);


        /*------------------------- Main Frame -------------------------*/
        frame.add(splitPane, BorderLayout.CENTER);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(rightPanel, BorderLayout.EAST);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1020,800);
        frame.setMinimumSize(new Dimension(617,548));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        /*---------- Loaders ----------*/
        FullScreenPhotoController fullScreenPhotoController = new FullScreenPhotoController(centralPanel, rightPanel);
        ImageCollectionLoader imageCollectionLoader = new ImageCollectionLoader(centralPanel, rightPanel, topPanel, leftPanel);

        // Initialize list
        imageCollectionLoader.initializeListModel();
    }
}