package ru.photoBolter.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.photoBolter.Constants;
import ru.photoBolter.controller.ChangeCurrentFileObservable;

import java.nio.file.Path;

import static ru.photoBolter.Constants.PHOTO_HEIGHT;
import static ru.photoBolter.Constants.PHOTO_WIDTH;

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
        imageView.setPreserveRatio(true);
        handlePortraitLandscape(image);
        imageView.setImage(image);
    }

    private void handlePortraitLandscape(Image image) {
        if (image.getHeight() > image.getWidth()) {
            imageView.setFitWidth(0);
            imageView.setFitHeight(PHOTO_HEIGHT);
        } else {
            imageView.setFitWidth(PHOTO_WIDTH);
            imageView.setFitHeight(0);
        }
    }

    @Override
    public void changeCurrentFile(Path currentFile) {
        setPathToFile(currentFile);
        refresh();
    }
}
