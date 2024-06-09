package S30173Baltovskyi.model;

import S30173Baltovskyi.controller.listeners.PhotoCollectionChangeListener;

import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class PhotoCollectionListModel extends AbstractListModel<PhotoCollection>
        implements Serializable, PhotoCollectionChangeListener {
    @Serial
    private static final long serialVersionUID = 1L;

    private final ArrayList<PhotoCollection> collectionsList = new ArrayList<>();
    private final String serFileName = "photoCollectionList.ser";

    @Override
    public int getSize() {
        return collectionsList.size();
    }

    @Override
    public PhotoCollection getElementAt(int index) {
        return collectionsList.get(index);
    }

    @Override
    public void onChangePhotoCollection(PhotoCollection photoCollection) {
        saveList();
    }

    public ArrayList<PhotoCollection> getCollectionsList() {
        return collectionsList;
    }

    public int add(PhotoCollection photoCollection) {
        File destDir = new File("rawPhotos", photoCollection.getUuid());
        if (!destDir.exists()) destDir.mkdirs();

        collectionsList.add(photoCollection);
        photoCollection.setCollectionChangeListener(this);

        int index = collectionsList.size() - 1;
        fireIntervalAdded(this, index, index);

        saveList();
        return index;
    }

    public void remove(int index) {
        String dirPath = "rawPhotos/" + collectionsList.get(index).getUuid();
        try {
            deleteDirectory(Paths.get(dirPath));
        } catch (IOException e) {
            System.out.println("Error removing directory: " + dirPath);
        }

        collectionsList.get(index).clearCache();
        collectionsList.remove(index);
        fireIntervalRemoved(this, index, index);
        saveList();
    }

    private void deleteDirectory(Path path) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }


    public void loadList() {
        File file = new File(serFileName);
        if (!file.exists() || file.length() == 0) {
            createEmptyListFile();
        }
        loadAndInitializeList();
    }

    private void createEmptyListFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(serFileName))) {
            oos.writeObject(new ArrayList<PhotoCollection>());
        } catch (IOException e) {
            System.out.println("Error creating empty list file: " + serFileName);
        }
    }

    private void loadAndInitializeList() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(serFileName))) {
            ArrayList<PhotoCollection> loadedList = (ArrayList<PhotoCollection>) ois.readObject();
            collectionsList.addAll(loadedList);

            initializeCollectionsAndPhotos();

            fireContentsChanged(this, 0, getSize() - 1);
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found while loading list: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error while loading list: " + e.getMessage());
        }
    }

    private void initializeCollectionsAndPhotos() {
        for (PhotoCollection collection : collectionsList) {
            collection.setCollectionChangeListener(this);
            for (Photo photo : collection.getPhotos())
                photo.setPhotoChangeListener(collection);
        }
    }

    private void saveList() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(serFileName))) {
            oos.writeObject(collectionsList);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving: " + serFileName);
        }
    }
}