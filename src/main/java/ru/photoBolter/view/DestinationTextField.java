package ru.photoBolter.view;

import javafx.scene.control.TextField;
import ru.photoBolter.Constants;
import ru.photoBolter.controller.observable.ChangeDestinationDirectoryObservable;

import java.nio.file.Path;

public class DestinationTextField implements ChangeDestinationDirectoryObservable {
    private final TextField textField = new TextField("text");

    {
        textField.setMinWidth(Constants.PHOTO_WIDTH);
    }

    @Override
    public void changeDestinationDirectory(Path path) {
            textField.setText("Destination directory: " + path.toString());
    }

    public TextField getView() {
        return textField;
    }
}
