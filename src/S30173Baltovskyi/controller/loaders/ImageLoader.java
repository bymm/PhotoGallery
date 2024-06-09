package S30173Baltovskyi.controller.loaders;

import S30173Baltovskyi.model.Photo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ImageLoader {
    private final JList<Photo> list;
    private final Set<Photo> pendingImages = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private final Map<Photo, ImageIcon> loadedImages = new ConcurrentHashMap<>();

    public ImageLoader(JList<Photo> list) {
        this.list = list;
    }

    class ImageLoadingWorker extends SwingWorker<ImageIcon, Void> {
        private final Photo photo;
        private final int width;
        private final int height;

        ImageLoadingWorker(Photo photo, int width, int height) {
            this.photo = photo;
            this.width = width;
            this.height = height;
            pendingImages.add(photo);
        }

        @Override
        protected ImageIcon doInBackground() {
            try {
                BufferedImage bufferedImage = ImageIO.read(new File(photo.getImagePath()));
                if (bufferedImage != null) {

                    int imageWidth = bufferedImage.getWidth();
                    int imageHeight = bufferedImage.getHeight();
                    int minDim = Math.min(imageWidth, imageHeight);

                    int cropX = (imageWidth - minDim)/2;
                    int cropY = (imageHeight - minDim)/2;

                    BufferedImage croppedImage = bufferedImage.getSubimage(cropX, cropY, minDim, minDim);
                    Image resizedImage = croppedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                    ImageIcon resizedImageIcon = new ImageIcon(resizedImage);

                    loadedImages.put(photo, resizedImageIcon);
                    pendingImages.remove(photo);
                    return resizedImageIcon;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
            return null;
        }

        @Override
        protected void done() {
            list.repaint();
        }
    }

    public void clearCache() {
        loadedImages.clear();
    }

    public ImageIcon getImageIcon(Photo photo) {
        if (!loadedImages.containsKey(photo)) {
            if (!pendingImages.contains(photo)) {
                ImageLoader.ImageLoadingWorker worker = new ImageLoadingWorker(photo, 190, 190);
                worker.execute();
            }
            return null;
        }
        return loadedImages.get(photo);
    }
}