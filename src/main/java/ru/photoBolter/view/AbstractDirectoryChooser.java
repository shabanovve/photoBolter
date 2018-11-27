package ru.photoBolter.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public abstract class AbstractDirectoryChooser {

    protected File initialDirectory;
    protected Stage stage;

    protected DirectoryChooser directoryChooser = new DirectoryChooser();
    protected Button openButton = new Button(getButtonText());

    protected abstract String getButtonText();
    protected abstract EventHandler<ActionEvent> getButtonAction();


    public AbstractDirectoryChooser() {
        openButton.setOnAction(getButtonAction());
        directoryChooser.setInitialDirectory(getInitialDirectory());
    }

    public Button getView() {
        return openButton;
    }

    public void setInitialDirectory(File initialDirectory) {
        this.initialDirectory = initialDirectory;
    }

    public File getInitialDirectory() {
        return initialDirectory;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}