package ru.photoBolter.view;

import javafx.scene.control.TextField;
import ru.photoBolter.Constants;
import ru.photoBolter.controller.ChangeCurrentFileObservable;

import java.nio.file.Path;

public class DateTextField implements ChangeCurrentFileObservable {
    private final TextField textField = new TextField("text");

    {
        textField.setMinWidth(Constants.PHOTO_WIDTH);
    }

    @Override
    public void changeCurrentFile(Path currentFile) {

        textField.setText("Created:");
    }

    public TextField getView() {
        return textField;
    }

}
