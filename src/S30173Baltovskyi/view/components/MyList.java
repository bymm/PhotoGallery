package S30173Baltovskyi.view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MyList<E> extends JList<E> {
    public MyList(ListModel<E> dataModel) {
        super(dataModel);

        MouseAdapter mouseAdapter = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                handleEmptyAreaClick(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                handleEmptyAreaClick(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                handleEmptyAreaClick(e);
            }

            private void handleEmptyAreaClick(MouseEvent e) {
                int index = locationToIndex(e.getPoint());
                Rectangle cellBounds = index != -1 ? getCellBounds(index, index) : null;

                if (index == -1 || (cellBounds != null && !cellBounds.contains(e.getPoint()))) {
                    clearSelection();
                    getSelectionModel().setAnchorSelectionIndex(-1);
                    getSelectionModel().setLeadSelectionIndex(-1);
                }
            }
        };

        // Add mouse listener to handle empty area clicks
        addMouseListener(mouseAdapter);

        // Add mouse motion listener to handle hover effect
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point point = e.getPoint();
                int index = locationToIndex(point);

                if (index != -1 && getCellBounds(index, index).contains(point))
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                else
                    setCursor(Cursor.getDefaultCursor());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                mouseAdapter.mouseDragged(e);
            }
        });

    }
}
