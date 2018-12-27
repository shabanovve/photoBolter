package ru.photoBolter.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ru.photoBolter.controller.observable.ChangeDestinationDirectoryObservable;
import ru.photoBolter.controller.observer.ChangeDestinationDirectoryObserver;

import java.io.File;
import java.nio.file.Path;

public class DestinationDirectoryChooser extends AbstractDirectoryChooser implements ChangeDestinationDirectoryObservable {

    private ChangeDestinationDirectoryObserver changeSoureDirectoryObserver;

    public void setObserver(ChangeDestinationDirectoryObserver changeSoureDirectoryObserver) {
        this.changeSoureDirectoryObserver = changeSoureDirectoryObserver;
    }

    @Override
    protected String getButtonText() {
        return "Select Destination directory";
    }

    @Override
    protected EventHandler<ActionEvent> getButtonAction() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                directoryChooser.setInitialDirectory(getInitialDirectory());
                File directory = directoryChooser.showDialog(stage);
                if (directory != null) {
                    changeSoureDirectoryObserver.changeDirectory(directory.toPath());
                }

            }
        };
    }

    @Override
    public void changeDestinationDirectory(Path path) {
        setInitialDirectory(path.toFile());
    }
}
