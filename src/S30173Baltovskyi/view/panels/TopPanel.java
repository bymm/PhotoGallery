package S30173Baltovskyi.view.panels;

import S30173Baltovskyi.view.components.MyButton;
import S30173Baltovskyi.view.components.MyLabel;
import S30173Baltovskyi.view.components.MyTextField;
import S30173Baltovskyi.view.icons.ButtonIconHelper;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel {
    private JComboBox<String> searchByList;
    private MyTextField searchTextField;
    private MyButton renameColButton;
    private MyButton removeColButton;
    private MyButton addColButton;


    public TopPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0));
        setBackground(new Color(0,122,204));

        createSearchByList();
        createSearchTextField();
        createCollectionButtons();
    }

    private void createSearchByList() {
        JLabel searchByLabel = new MyLabel("Search by:");

        String[] searchByOptions = { "Name", "Tags", "Dates", "Words" };

        searchByList = new JComboBox<>(searchByOptions);
        searchByList.setPreferredSize(new Dimension(105, 30));
        searchByList.setMaximumSize(new Dimension(105, 30));
        searchByList.setFont(new Font("Arial", Font.PLAIN, 16));
        searchByList.setOpaque(true);
        searchByList.setBackground(new Color(0,122,204));
        searchByList.setForeground(new Color(62,62,66));

        add(searchByLabel);
        add(searchByList);
    }

    private void createSearchTextField() {
        searchTextField = new MyTextField("Search", new Dimension(500,45),
                new Dimension(600,45), true);
        searchTextField.setRoundedCorners(true);

        add(Box.createHorizontalGlue());
        add(searchTextField);
        add(Box.createHorizontalStrut(5));
    }

    private void createCollectionButtons() {
        renameColButton = new MyButton("");
        renameColButton.setVisible(false);
        ButtonIconHelper.setIconOnButton(renameColButton, "/S30173Baltovskyi/view/icons/editIcon.png", 50, 50);

        removeColButton = new MyButton("");
        removeColButton.setVisible(false);
        ButtonIconHelper.setIconOnButton(removeColButton, "/S30173Baltovskyi/view/icons/removeIcon.png", 40, 40);

        addColButton = new MyButton("");
        ButtonIconHelper.setIconOnButton(addColButton, "/S30173Baltovskyi/view/icons/addIcon.png", 60, 60);


        add(Box.createHorizontalGlue());
        add(renameColButton);
        add(removeColButton);
        add(addColButton);
    }

    public MyTextField getSearchTextField() {
        return searchTextField;
    }

    public JComboBox<String> getSearchByList() {
        return searchByList;
    }

    public MyButton getRenameColButton() {
        return renameColButton;
    }

    public MyButton getRemoveColButton() {
        return removeColButton;
    }

    public MyButton getAddColButton() {
        return addColButton;
    }
}