package ru.photoBolter.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import ru.photoBolter.controller.ChangeDirectoryObserver;

import java.io.File;

public abstract class AbstractDirectoryChooser {

    protected DirectoryChooser directoryChooser = new DirectoryChooser();
    protected Button openButton = new Button(getButtonText());
    protected ChangeDirectoryObserver changeSoureDirectoryObserver;

    protected abstract String getButtonText();
    protected abstract EventHandler<ActionEvent> getButtonAction();
    protected abstract File getInitialDirectory();


    public AbstractDirectoryChooser() {
        openButton.setOnAction(getButtonAction());
        directoryChooser.setInitialDirectory(getInitialDirectory());
    }

    public Button getView() {
        return openButton;
    }

    public void setObserver(ChangeDirectoryObserver changeSoureDirectoryObserver) {
        this.changeSoureDirectoryObserver = changeSoureDirectoryObserver;
    }
}