package project.controller.loaders;

import project.controller.workers.ImageLoaderWorker;
import project.model.Photo;
import project.model.PhotoCollection;
import project.view.dialogs.MyBaseDialog;
import project.view.panels.CentralPanel;
import project.view.panels.LeftPanel;
import project.view.panels.RightPanel;
import project.view.panels.TopPanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class ImageCollectionLoader {
    private final CentralPanel centralPanel;
    private final RightPanel rightPanel;
    private final TopPanel topPanel;
    private final LeftPanel leftPanel;

    public ImageCollectionLoader(CentralPanel centralPanel, RightPanel rightPanel,
                                 TopPanel topPanel, LeftPanel leftPanel) {
        this.centralPanel = centralPanel;
        this.rightPanel = rightPanel;
        this.topPanel = topPanel;
        this.leftPanel = leftPanel;

        /*------------------------------ Collection Events ------------------------------*/
        // Add new collection
        topPanel.getAddColButton().addActionListener(e -> addNewCollection());

        // Remove current collection
        topPanel.getRemoveColButton().addActionListener(e -> removeCurrentCollection());

        // Rename current collection
        topPanel.getRenameColButton().addActionListener(e -> renameCurrentCollection());

        // Collection selection
        rightPanel.getCollectionList().addListSelectionListener(e -> handleCollectionSelection());


        // Add image(s) to current collection
        centralPanel.getAddImageButton().addActionListener(e -> addImagesToCollection());

        // Remove image(s) from current collection
        centralPanel.getRemoveImageButton().addActionListener(e -> removeImagesFromCollection());

        // Edit selected image's info
        centralPanel.getEditImageInfoButton().addActionListener(e -> editImageInfo());

        // Image selection
        centralPanel.getPhotoList().addListSelectionListener(e -> handleImageSelection());
    }

    private void addNewCollection() {
        String collectionTitle = MyBaseDialog.showInputDialog(centralPanel, "Add Collection", "Title:", "");
        if (collectionTitle != null && !collectionTitle.isEmpty()) {
            // Add new PhotoCollection, get index
            int addedIndex = rightPanel.getCollectionListModel().add(new PhotoCollection(collectionTitle));

            // Choose added collection
            rightPanel.getCollectionList().setSelectedIndex(addedIndex);
        }
    }

    private void removeCurrentCollection() {
        boolean confirmed = MyBaseDialog.showConfirmDialog(centralPanel,"Remove Selected Collection?");

        if (confirmed) {
            // Get Selected Collection to remove
            int selectedIndex = rightPanel.getCollectionList().getSelectedIndex();

            // Remove current collection
            rightPanel.getCollectionListModel().remove(selectedIndex);

            // Clear view
            leftPanel.setComponentsVisible(false);
            rightPanel.getCollectionList().clearSelection();
        }
    }

    private void renameCurrentCollection() {
        // Get Selected Collection to rename
        int selectedIndex = rightPanel.getCollectionList().getSelectedIndex();
        String currentTitle =  rightPanel.getCollectionList().getSelectedValue().getTitle();

        String newTitle = MyBaseDialog.showInputDialog(centralPanel,"Rename Collection", "New Title:", currentTitle);

        if (newTitle != null && !newTitle.isEmpty()) {
            // Set new title
            rightPanel.getCollectionListModel().getElementAt(selectedIndex).setTitle(newTitle);

            // Update view
            centralPanel.getCurColTitle().setText(newTitle);
        }
    }

    private void handleCollectionSelection() {
        if (!rightPanel.getCollectionList().getValueIsAdjusting()) {
            if (!rightPanel.getCollectionList().isSelectionEmpty() &&
                    rightPanel.getCollectionList().getSelectedIndex() != -1) {

                // Show: Images, Collection Header, Rename and Remove Buttons, but do not show left panel
                centralPanel.getPhotoList().setVisible(true);
                centralPanel.getColHeaderPanel().setVisible(true);
                topPanel.getRenameColButton().setVisible(true);
                topPanel.getRemoveColButton().setVisible(true);
                leftPanel.setComponentsVisible(false);

                // Clear Selection for previous collection
                centralPanel.getPhotoList().clearSelection();

                // Update Title, NumPhotos, Images
                PhotoCollection selectedCollection = rightPanel.getCollectionList().getSelectedValue();

                // Set Current Collection Title
                centralPanel.getCurColTitle().setText(selectedCollection.getTitle());

                // Set Current Collection Number of Photos
                centralPanel.getCurColNumPhotos().setText(Integer.toString(selectedCollection.getPhotos().size()));

                // Set new collection to show
                centralPanel.getPhotoListModel().setPhotoCollection(selectedCollection);

            } else {
                centralPanel.getPhotoList().setVisible(false);
                centralPanel.getColHeaderPanel().setVisible(false);
                topPanel.getRenameColButton().setVisible(false);
                topPanel.getRemoveColButton().setVisible(false);
                leftPanel.setComponentsVisible(false);
            }
        }
    }


    private void addImagesToCollection() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Images");
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setFileFilter(new FileNameExtensionFilter(
                ".png, .jpg, .jpeg, .gif, .bmp, .wbmp",
                "png", "jpg", "jpeg", "gif", "bmp", "wbmp")
        );

        if (fileChooser.showOpenDialog(centralPanel) == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();

            // Get collection to add photos to it
            PhotoCollection selectedCollection = rightPanel.getCollectionList().getSelectedValue();

            // Start Copying selected files to project directory
            new ImageLoaderWorker(files, selectedCollection, centralPanel).execute();
        }
    }

    private void removeImagesFromCollection() {
        // Get Selected indexes to remove
        int[] selectedIndices = centralPanel.getPhotoList().getSelectedIndices();

        // Show dialog
        boolean confirmed = MyBaseDialog.showConfirmDialog(centralPanel,
                String.format("Remove %d Selected Photo(s)?", selectedIndices.length)
        );

        if (confirmed) {
            // Get selected collection
            PhotoCollection selectedCollection = rightPanel.getCollectionList().getSelectedValue();

            // Remove each selected photo, starting from the last
            for (int i = selectedIndices.length - 1; i >= 0; i--)
                selectedCollection.removePhotoAt(selectedIndices[i]);

            // Update view
            centralPanel.getCurColNumPhotos().setText(Integer.toString(selectedCollection.getPhotos().size()));
            centralPanel.getPhotoList().clearSelection();
            centralPanel.getPhotoListModel().updatePhotos();
        }
    }

    private void editImageInfo() {
        // Get last selected index
        int leadIndex = centralPanel.getPhotoList().getLeadSelectionIndex();

        // Get last selected photo to change info
        Photo lastSelectedPhoto = centralPanel.getPhotoListModel().getElementAt(leadIndex);

        // Show Dialog
        MyBaseDialog.showEditImageInfoDialog(centralPanel, lastSelectedPhoto);

        // Update info about the selected photo on the left panel
        leftPanel.setPhoto(lastSelectedPhoto);
    }

    private void handleImageSelection() {
        if (!centralPanel.getPhotoList().getValueIsAdjusting()) {
            if (!centralPanel.getPhotoList().isSelectionEmpty() &&
                    centralPanel.getPhotoList().getSelectedIndex() != -1) {

                // Get last selected index
                int leadIndex = centralPanel.getPhotoList().getLeadSelectionIndex();

                // Get last selected photo
                Photo lastSelectedPhoto = centralPanel.getPhotoListModel().getElementAt(leadIndex);

                // Show info about that photo on the left panel
                leftPanel.setPhoto(lastSelectedPhoto);

                // Show button to remove/changeInfo image
                centralPanel.getRemoveImageButton().setVisible(true);
                centralPanel.getEditImageInfoButton().setVisible(true);
            } else {
                // Selection is empty, hide UI
                leftPanel.setPhoto(null);
                centralPanel.getRemoveImageButton().setVisible(false);
                centralPanel.getEditImageInfoButton().setVisible(false);
            }
        }
    }

    public void initializeListModel() {
        rightPanel.getCollectionListModel().loadList();
    }
}