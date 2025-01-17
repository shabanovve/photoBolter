package ru.photoBolter.view;

import javafx.scene.control.TextField;
import ru.photoBolter.Constants;
import ru.photoBolter.controller.observable.ChangeSourceDirectoryModelObservable;

import java.nio.file.Path;

public class SourceTextField implements ChangeSourceDirectoryModelObservable {
    private final TextField textField = new TextField("text");

    {
        textField.setMinWidth(Constants.PHOTO_WIDTH);
    }

    @Override
    public void changeSourceDirectory(Path path) {
        textField.setText("Source directory: " + path.toString());
    }

    public TextField getView() {
        return textField;
    }

}
