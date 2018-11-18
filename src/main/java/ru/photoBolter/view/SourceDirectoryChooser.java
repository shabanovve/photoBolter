package ru.photoBolter.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.io.File;

public class SourceDirectoryChooser extends AbstractDirectoryChooser {

    private File initialDirectory;
    private Stage stage;

    public SourceDirectoryChooser(File initialDirectory) {
        this.initialDirectory = initialDirectory;
    }

    public File getInitialDirectory() {
        return initialDirectory;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    protected String getButtonText() {
        return "Select Source directory";
    }

    @Override
    protected EventHandler<ActionEvent> getButtonAction() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File directory = directoryChooser.showDialog(stage);
                changeSoureDirectoryObserver.changeDirectory(directory.toPath());
            }
        };
    }
}
