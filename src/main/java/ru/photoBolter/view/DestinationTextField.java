package ru.photoBolter.view;

import javafx.scene.control.TextField;
import ru.photoBolter.controller.ChangeDestinationDirectoryObservable;

import java.nio.file.Path;

public class DestinationTextField implements ChangeDestinationDirectoryObservable {
    private final TextField textField = new TextField("text");

    @Override
    public void changeDestinationDirectory(Path path) {
            textField.setText(path.toString());
    }

    public TextField getView() {
        return textField;
    }
}
