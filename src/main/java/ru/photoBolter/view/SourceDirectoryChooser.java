package ru.photoBolter.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ru.photoBolter.controller.observable.ChangeSourceDirectoryModelObservable;
import ru.photoBolter.controller.observer.ChangeSoureDirectoryObserver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class SourceDirectoryChooser extends AbstractDirectoryChooser implements ChangeSourceDirectoryModelObservable {

    private ChangeSoureDirectoryObserver changeSoureDirectoryObserver;

    public void setObserver(ChangeSoureDirectoryObserver changeSoureDirectoryObserver) {
        this.changeSoureDirectoryObserver = changeSoureDirectoryObserver;
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
                directoryChooser.setInitialDirectory(getInitialDirectory());
                File directory = directoryChooser.showDialog(stage);
                if (directory != null && Files.exists(directory.toPath())) {
                    changeSoureDirectoryObserver.changeDirectory(directory.toPath());
                }
            }
        };
    }

    @Override
    public void changeSourceDirectory(Path path) {
        setInitialDirectory(path.toFile());
    }
}
