package ru.photoBolter.view;

import javafx.scene.control.TextField;
import ru.photoBolter.Constants;
import ru.photoBolter.controller.observable.ChangeDestinationDirectoryModelObservable;

public class DestinationTextField implements ChangeDestinationDirectoryModelObservable {
    private final TextField textField = new TextField("text");

    {
        textField.setMinWidth(Constants.PHOTO_WIDTH);
    }

    @Override
    public void changeDestinationDirectory(String destinationDirectory) {
        textField.setText("Destination directory: " + destinationDirectory);
    }

    public TextField getView() {
        return textField;
    }
}
