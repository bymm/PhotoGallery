package project.view.icons;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Image;
import java.util.Objects;

public class ButtonIconHelper {
    public static void setIconOnButton(JButton button, String iconPath, int width, int height) {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(ButtonIconHelper.class.getResource(iconPath)));


        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(img);

        button.setIcon(resizedIcon);

        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.CENTER);
        button.setIconTextGap(0);
    }
}
