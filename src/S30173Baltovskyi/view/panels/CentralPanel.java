package S30173Baltovskyi.view.panels;

import S30173Baltovskyi.controller.loaders.ImageLoader;
import S30173Baltovskyi.model.Photo;
import S30173Baltovskyi.model.PhotoListModel;
import S30173Baltovskyi.view.components.MyButton;
import S30173Baltovskyi.view.components.MyList;
import S30173Baltovskyi.view.components.MyScrollPane;
import S30173Baltovskyi.view.components.MyTextAreaNoWrap;
import S30173Baltovskyi.view.icons.ButtonIconHelper;
import S30173Baltovskyi.view.renderers.PhotoListCellRenderer;

import javax.swing.*;
import java.awt.*;

public class CentralPanel extends JPanel {
    private JPanel colHeaderPanel;
    private MyTextAreaNoWrap curColTitle;
    private MyTextAreaNoWrap curColNumPhotos;
    private MyButton editImageInfoButton;
    private MyButton removeImageButton;
    private MyButton addImageButton;
    private PhotoListModel photoListModel;
    private MyList<Photo> photoList;
    private ImageLoader imageLoader;


    public CentralPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(45, 45, 48));

        createHeaderPanel();
        createImagesList();
    }

    private void createHeaderPanel() {
        // Current Collection Header
        colHeaderPanel = new JPanel();
        colHeaderPanel.setLayout(new BoxLayout(colHeaderPanel, BoxLayout.X_AXIS));
        colHeaderPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        colHeaderPanel.setBackground(new Color(65, 99, 246));
        colHeaderPanel.setVisible(false);

        // Title, Number of photos
        JPanel colHeaderLeftPanel = new JPanel(new BorderLayout());
        colHeaderLeftPanel.setBackground(new Color(65, 99, 246));

        curColTitle = new MyTextAreaNoWrap("");
        curColTitle.setPreferredSize(new Dimension(0,0));
        curColNumPhotos = new MyTextAreaNoWrap("");

        colHeaderLeftPanel.add(curColTitle, BorderLayout.CENTER);
        colHeaderLeftPanel.add(curColNumPhotos, BorderLayout.SOUTH);


        // Add/Remove/ChangeInfo Image Buttons Panel
        JPanel addRemoveImageButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0,0));
        addRemoveImageButtonsPanel.setBackground(new Color(65, 99, 246));

        editImageInfoButton = new MyButton("");
        editImageInfoButton.setVisible(false);
        ButtonIconHelper.setIconOnButton(editImageInfoButton, "/S30173Baltovskyi/view/icons/editIcon.png", 50, 50);

        removeImageButton = new MyButton("");
        removeImageButton.setVisible(false);
        ButtonIconHelper.setIconOnButton(removeImageButton, "/S30173Baltovskyi/view/icons/removeIcon.png", 40, 40);

        addImageButton = new MyButton("");
        ButtonIconHelper.setIconOnButton(addImageButton, "/S30173Baltovskyi/view/icons/addIcon.png", 60, 60);

        addRemoveImageButtonsPanel.add(editImageInfoButton);
        addRemoveImageButtonsPanel.add(removeImageButton);
        addRemoveImageButtonsPanel.add(addImageButton);

        colHeaderPanel.add(colHeaderLeftPanel);
        colHeaderPanel.add(Box.createHorizontalStrut(5));
        colHeaderPanel.add(Box.createHorizontalGlue());
        colHeaderPanel.add(addRemoveImageButtonsPanel);

        add(colHeaderPanel, BorderLayout.NORTH);
    }

    private void createImagesList() {

        photoListModel = new PhotoListModel();
        photoList = new MyList<>(photoListModel);
        imageLoader = new ImageLoader(photoList);

        photoList.setCellRenderer(new PhotoListCellRenderer(imageLoader));
        photoList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        photoList.setFixedCellHeight(200);
        photoList.setFixedCellWidth(200);
        photoList.setBackground(new Color(45, 45, 48));
        photoList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        photoList.setVisibleRowCount(-1);

        MyScrollPane imagesScroll = new MyScrollPane(photoList);
        add(imagesScroll, BorderLayout.CENTER);
    }

    public JPanel getColHeaderPanel() {
        return colHeaderPanel;
    }

    public MyTextAreaNoWrap getCurColTitle() {
        return curColTitle;
    }

    public MyTextAreaNoWrap getCurColNumPhotos() {
        return curColNumPhotos;
    }

    public MyButton getEditImageInfoButton() {
        return editImageInfoButton;
    }

    public MyButton getRemoveImageButton() {
        return removeImageButton;
    }

    public MyButton getAddImageButton() {
        return addImageButton;
    }

    public MyList<Photo> getPhotoList() {
        return photoList;
    }

    public PhotoListModel getPhotoListModel() {
        return photoListModel;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
