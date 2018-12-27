package ru.photoBolter.view;

import javafx.scene.control.TextField;
import ru.photoBolter.Constants;
import ru.photoBolter.controller.observable.ChangeModelCurrentFileObservable;
import ru.photoBolter.model.PathContainer;

public class DateTextField implements ChangeModelCurrentFileObservable {
    private final TextField textField = new TextField("Created:");

    {
        textField.setMinWidth(Constants.PHOTO_WIDTH);
    }

    @Override
    public void changeCurrentFile(PathContainer pathContainer) {
        textField.setText(String.format("Created: %s", pathContainer.getCreateDate()));
    }

    public TextField getView() {
        return textField;
    }

}
