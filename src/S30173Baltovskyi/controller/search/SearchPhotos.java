package S30173Baltovskyi.controller.search;

import S30173Baltovskyi.model.Photo;
import S30173Baltovskyi.model.PhotoCollection;
import S30173Baltovskyi.view.panels.CentralPanel;
import S30173Baltovskyi.view.panels.RightPanel;
import S30173Baltovskyi.view.panels.TopPanel;

import java.util.ArrayList;

public class SearchPhotos {
    TopPanel topPanel;
    CentralPanel centralPanel;
    RightPanel rightPanel;

    public SearchPhotos(TopPanel topPanel, CentralPanel centralPanel, RightPanel rightPanel) {
        this.topPanel = topPanel;
        this.centralPanel = centralPanel;
        this.rightPanel = rightPanel;

        topPanel.getSearchTextField().addActionListener(e -> searchPhotos());
    }

    private void searchPhotos() {
        String searchCriteria = (String) topPanel.getSearchByList().getSelectedItem();
        String searchText = topPanel.getSearchTextField().getText().trim();

        PhotoCollection selectedCollection = rightPanel.getCollectionList().getSelectedValue();

        if (selectedCollection == null) {
            // search in all collections
            System.out.println("Start searching in all collections");
            System.out.println("Searching by: " + searchCriteria);
            System.out.println("Search text: " + searchText);
            ArrayList<PhotoCollection> allCollections = rightPanel.getCollectionListModel().getCollectionsList();
        } else {
            // search in selected collection
            System.out.println("Start searching in selected collection");
            System.out.println("Searching by: " + searchCriteria);
            System.out.println("Search text: " + searchText);
             ArrayList<Photo> photos = selectedCollection.getPhotos();
        }

        // Search based on selected criteria
        if (searchCriteria != null && !searchText.isEmpty()) {
            switch (searchCriteria) {
                case "Name" -> System.out.println("We are in Name");
                case "Tags" -> System.out.println("We are in tags");
                case "Dates" -> System.out.println("We are in dates");
                case "Words" -> System.out.println("We are in words");
            }
        }
    }
}
