package ru.photoBolter.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ru.photoBolter.controller.ChangeDestinationDirectoryObserver;

import java.io.File;

public class DestinationDirectoryChooser extends AbstractDirectoryChooser  {

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
                File directory = directoryChooser.showDialog(stage);
                if (directory != null) {
                    changeSoureDirectoryObserver.changeDirectory(directory.toPath());
                }

            }
        };
    }
}
