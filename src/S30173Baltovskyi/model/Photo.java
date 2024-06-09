package S30173Baltovskyi.model;

import S30173Baltovskyi.controller.listeners.PhotoChangeListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Photo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String title;
    private final ArrayList<String> tags;
    private String date;
    private String description;
    private final String imagePath;

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

    public void setPhotoChangeListener(PhotoChangeListener photoChangeListener) {
        this.photoChangeListener = photoChangeListener;
    }

    public String printTags() {
        if (tags.isEmpty())
            return "";

        StringBuilder printedTags = new StringBuilder();
        for (String tag : tags)
            printedTags.append('#').append(tag).append(' ');
        return printedTags.toString().trim();
    }

    private void notifyPhotoChangeListener() {
        if (photoChangeListener != null)
            photoChangeListener.onChangePhoto(this);
    }
}