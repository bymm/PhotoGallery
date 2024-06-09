package project.controller.photoprocessors;

import project.view.components.MyButton;
import project.view.components.MyLabel;
import project.view.components.MyTextAreaNoWrap;
import project.view.dialogs.MyBaseDialog;
import project.view.panels.CentralPanel;
import project.view.panels.RightPanel;
import project.model.Photo;
import project.model.PhotoCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class FullScreenPhotoController {
    private JFrame slideFrame;
    private final CentralPanel mainCentralPanel;
    private final RightPanel mainRightPanel;

    private ArrayList<Photo> photos;
    private Photo curPhoto;
    private int curIndex;

    private JPanel thisTopPanel;
    private MyTextAreaNoWrap curPhotoTitle;
    private MyTextAreaNoWrap curPhotoDate;

    private JPanel thisCenterPanel;
    private JLabel curPhotoLabel;

    private JPanel thisBottomPanel;
    private MyButton prevPhotoButton;
    private MyButton nextPhotoButton;
    private MyLabel photoIndexLabel;

    public FullScreenPhotoController(CentralPanel mainCentralPanel, RightPanel mainRightPanel) {
        this.mainCentralPanel = mainCentralPanel;
        this.mainRightPanel = mainRightPanel;

        setupFullScreenKey();
    }

    private void setupFullScreenKey() {
        JList<Photo> photoList = mainCentralPanel.getPhotoList();

        photoList.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F"), "launchFullScreen");
        photoList.getActionMap().put("launchFullScreen", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launchFullScreenPhotoViewer();
            }
        });
    }

    private void launchFullScreenPhotoViewer() {
        PhotoCollection selectedCollection = mainRightPanel.getCollectionList().getSelectedValue();
        if (selectedCollection == null) {
            MyBaseDialog.showMessageDialog(thisCenterPanel, "Error", "No collection selected");
            return;
        }

        photos = selectedCollection.getPhotos();
        if (photos == null || photos.isEmpty()) {
            MyBaseDialog.showMessageDialog(thisCenterPanel, "Error", "No photos in collection");
            return;
        }

        curIndex = mainCentralPanel.getPhotoList().getLeadSelectionIndex();
        if (curIndex < 0 || curIndex >= photos.size()) {
            MyBaseDialog.showMessageDialog(thisCenterPanel, "Error", "No photos selected");
            return;
        }

        curPhoto = photos.get(curIndex);

        slideFrame = new JFrame("Collection: " + selectedCollection.getTitle());
        createTopPanel();
        createCenterPanel();
        createBottomPanel();

        slideFrame.add(thisTopPanel, BorderLayout.NORTH);
        slideFrame.add(thisCenterPanel, BorderLayout.CENTER);
        slideFrame.add(thisBottomPanel, BorderLayout.SOUTH);

        slideFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        slideFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        slideFrame.setUndecorated(true);
        slideFrame.setLocationRelativeTo(null);
        slideFrame.setVisible(true);
        slideFrame.setResizable(false);

        setupFrameKeys(slideFrame);

        SwingUtilities.invokeLater(() -> curPhotoLabel.setIcon(calculateDimsAndResize(curPhoto)));
    }

    private void createTopPanel() {
        thisTopPanel = new JPanel(new BorderLayout());
        thisTopPanel.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        thisTopPanel.setBackground(new Color(37, 37, 38));

        curPhotoTitle = new MyTextAreaNoWrap(curPhoto.getTitle());
        curPhotoDate = new MyTextAreaNoWrap(curPhoto.getDate().isEmpty() ? "no date" : curPhoto.getDate());

        thisTopPanel.add(curPhotoTitle, BorderLayout.WEST);
        thisTopPanel.add(curPhotoDate, BorderLayout.EAST);
    }

    private void createCenterPanel() {
        thisCenterPanel = new JPanel(new GridBagLayout());
        thisCenterPanel.setBackground(new Color(45, 45, 48));
        curPhotoLabel = new JLabel();
        thisCenterPanel.add(curPhotoLabel, new GridBagConstraints());

        thisCenterPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                curPhotoLabel.setIcon(calculateDimsAndResize(curPhoto));
            }
        });
    }

    private void createBottomPanel() {
        thisBottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40,0));
        thisTopPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        thisBottomPanel.setBackground(new Color(37, 37, 38));

        prevPhotoButton = new MyButton("<-");
        prevPhotoButton.setPreferredSize(new Dimension(60,40));
        prevPhotoButton.setEnabled(curIndex != 0);
        prevPhotoButton.addActionListener(e -> displayPreviousPhoto());
        thisBottomPanel.add(prevPhotoButton);

        photoIndexLabel = new MyLabel((curIndex + 1) + "/" + photos.size());
        thisBottomPanel.add(photoIndexLabel);

        nextPhotoButton = new MyButton("->");
        nextPhotoButton.setPreferredSize(new Dimension(60,40));
        nextPhotoButton.setEnabled(curIndex != photos.size() - 1);
        nextPhotoButton.addActionListener(e -> displayNextPhoto());
        thisBottomPanel.add(nextPhotoButton);
    }

    private void updatePhoto() {
            curPhoto = photos.get(curIndex);
            calculateDimsAndResize(curPhoto);
            curPhotoLabel.setIcon(calculateDimsAndResize(curPhoto));

            curPhotoTitle.setText(curPhoto.getTitle());
            curPhotoDate.setText(curPhoto.getDate().isEmpty() ? "no date" : curPhoto.getDate());
            photoIndexLabel.setText((curIndex + 1) + "/" + photos.size());

            prevPhotoButton.setEnabled(curIndex != 0);
            nextPhotoButton.setEnabled(curIndex != photos.size() - 1);
        }

    private void displayPreviousPhoto() {
        if (curIndex > 0) {
            curIndex--;
            updatePhoto();
        }
    }

    private void displayNextPhoto() {
        if (curIndex < photos.size() - 1) {
            curIndex++;
            updatePhoto();
        }
    }

    private void exitFullScreenViewer() {
        boolean confirmed = MyBaseDialog.showConfirmDialog(thisCenterPanel, "Do you want to exit fullscreen mode?");
        if (confirmed) {
            mainCentralPanel.getPhotoList().setSelectedIndex(curIndex);
            mainCentralPanel.getPhotoList().ensureIndexIsVisible(curIndex);
            slideFrame.dispose();
        }
    }

    private void setupFrameKeys(JFrame frame) {
        InputMap inputMap = frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = frame.getRootPane().getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "exitFullScreen");
        actionMap.put("exitFullScreen", new AbstractAction() {
           @Override
           public void actionPerformed(ActionEvent e) {
               exitFullScreenViewer();
           }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "displayPreviousPhoto");
        actionMap.put("displayPreviousPhoto", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPreviousPhoto();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "displayNextPhoto");
        actionMap.put("displayNextPhoto", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayNextPhoto();
            }
        });
    }

    private ImageIcon calculateDimsAndResize(Photo photo) {
        // Get view width, height
        int viewWidth = thisCenterPanel.getWidth();
        int viewHeight = thisCenterPanel.getHeight();

        // Get photo width, height, aspect ratio
        int imageWidth = photo.getOriginalWidth();
        int imageHeight = photo.getOriginalHeight();
        double imgAspectRatio = (double) imageWidth / imageHeight;

        // No need to resize
//        if (imageWidth <= viewWidth && imageHeight <= viewHeight)
//            return photo.getImageIcon();

        // Need to resize
        int newWidth, newHeight;
        if (imageWidth / (double) viewWidth > imageHeight / (double) viewHeight) {
            newWidth = viewWidth;
            newHeight = (int) (viewWidth / imgAspectRatio);
        } else {
            newHeight = viewHeight;
            newWidth = (int) (viewHeight * imgAspectRatio);
        }

        return photo.resizeImageIcon(newWidth, newHeight);
    }
}