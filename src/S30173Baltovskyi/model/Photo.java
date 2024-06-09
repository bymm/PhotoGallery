package project.model;

import project.controller.listeners.PhotoChangeListener;
import project.controller.workers.ImageBufferWorker;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Photo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String title;
    private final ArrayList<String> tags;
    private String date;
    private String description;
    private final String imagePath;

    private int originalWidth;
    private int originalHeight;

    private transient Map<Dimension, ImageIcon> resizedIconsCache = new HashMap<>();
    private transient PhotoChangeListener photoChangeListener;

    public Photo(String title, ArrayList<String> tags, String date, String description, String imagePath) {
        this.title = title;
        this.tags = tags;
        this.date = date;
        this.description = description;
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public int getOriginalWidth() {
        return originalWidth;
    }

    public int getOriginalHeight() {
        return originalHeight;
    }


    public void setTitle(String title) {
        this.title = title;
        notifyPhotoChangeListener();
    }

    public void setTags(String tags) {
        if (tags.trim().equals("no tags"))
            return;

        this.tags.clear();

        String[] split = tags.split("[#,\\s]+");

        for (String tag : split)
            if (!tag.trim().isEmpty())
                this.tags.add(tag.trim());

        notifyPhotoChangeListener();
    }

    public void setDate(String date) {
        if (date.trim().isEmpty()) {
            this.date = "";
            notifyPhotoChangeListener();
            return;
        }

        try {
            LocalDate parsedDate = LocalDate.parse(date);
            this.date = parsedDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            this.date = "";
        }
        notifyPhotoChangeListener();
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPhotoChangeListener();
    }

    public void setOriginalWidth(int originalWidth) {
        this.originalWidth = originalWidth;
    }

    public void setOriginalHeight(int originalHeight) {
        this.originalHeight = originalHeight;
    }

    public void setPhotoChangeListener(PhotoChangeListener photoChangeListener) {
        this.photoChangeListener = photoChangeListener;
    }


    public ImageIcon resizeImageIcon(int width, int height) {
        Dimension dimension = new Dimension(width, height);
        if (!resizedIconsCache.containsKey(dimension)) {
            new ImageBufferWorker(this, width, height).execute();
        }
        return resizedIconsCache.get(dimension);
    }

    public void putResizedIcon(Dimension dimension, ImageIcon resizedIcon) {
        resizedIconsCache.put(dimension, resizedIcon);
    }

    public String printTags() {
        if (tags.isEmpty())
            return "";

        StringBuilder printedTags = new StringBuilder();
        for (String tag : tags)
            printedTags.append('#').append(tag).append(' ');
        return printedTags.toString().trim();
    }

    public void clearResizedIconsCache() {
        resizedIconsCache.clear();
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        resizedIconsCache = new HashMap<>();
    }

    private void notifyPhotoChangeListener() {
        if (photoChangeListener != null)
            photoChangeListener.onChangePhoto(this);
    }
}