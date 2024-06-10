package S30173Baltovskyi;

import S30173Baltovskyi.controller.FullScreenPhotoController;
import S30173Baltovskyi.controller.loaders.ImageCollectionController;
import S30173Baltovskyi.model.Photo;
import S30173Baltovskyi.model.PhotoCollection;
import S30173Baltovskyi.controller.search.SearchPhotos;
import S30173Baltovskyi.view.panels.*;

import javax.swing.*;
import java.awt.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

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
//        PhotoCollection coll = new PhotoCollection("Test",
//            new ArrayList<Photo>(Arrays.stream(new Photo[]{
//                new Photo("Winter 2024", new ArrayList<String>(Arrays.stream(new String[]{"Ski", "Snow", "Fun"}).toList()), "", "Winter Holiday 2024", ""),
//                new Photo("Winter 2023", new ArrayList<String>(Arrays.stream(new String[]{"Ski", "Buk", "Snowboard"}).toList()), "", "Winter Holiday 2023", "")
//            }).toList())
//        );
//        PhotoCollection searchColl = coll.search("ski 2023");
//        searchColl.getPhotos().forEach(photo -> {
//            System.out.println(photo.getTitle());
//        });
        new S30173Baltovskyi();
    }
}