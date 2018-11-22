package ru.photoBolter.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.photoBolter.controller.ChangeCurrentFileObservable;

import java.nio.file.Path;

public class PhotoView implements ChangeCurrentFileObservable {
    private Path pathToFile;
    private final ImageView imageView = new ImageView();

    public ImageView getView() {
        return imageView;
    }

    public void setPathToFile(Path pathToFile) {
        this.pathToFile = pathToFile;
    }

    public void refresh() {
        Image image = new Image(pathToFile.toFile().toURI().toString());
        imageView.setImage(image);
    }

    @Override
    public void changeCurrentFile(Path currentFile) {
        setPathToFile(currentFile);
        refresh();
    }
}
