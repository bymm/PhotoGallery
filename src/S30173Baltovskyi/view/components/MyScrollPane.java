package S30173Baltovskyi.view.components;

import javax.swing.*;
import java.awt.*;

public class MyScrollPane extends JScrollPane {
    public MyScrollPane(Component view) {
        super(view);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        getVerticalScrollBar().setPreferredSize(new Dimension(7, getPreferredSize().height));

        setOpaque(false);
        getViewport().setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder());
    }
}
