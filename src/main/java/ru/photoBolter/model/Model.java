package ru.photoBolter.model;

import ru.photoBolter.controller.ChangeSourceDirectoryObservable;
import ru.photoBolter.controller.ChangeSoureDirectoryObserver;

import java.nio.file.Path;

public class Model implements ChangeSourceDirectoryObservable {

    private Path sourceDirectory;

    public void setChangeSoureDirectoryObserver(ChangeSoureDirectoryObserver changeSoureDirectoryObserver) {
        this.changeSoureDirectoryObserver = changeSoureDirectoryObserver;
    }

    private ChangeSoureDirectoryObserver changeSoureDirectoryObserver;

    public Path getSourceDirectory() {
        return this.sourceDirectory;
    }

    public void setSourceDirectory(Path sourceDirectory) {
        changeSourceDirectory(sourceDirectory);
    }

    @Override
    public void changeSourceDirectory(Path path) {
        this.sourceDirectory = path;
        changeSoureDirectoryObserver.changeDirectory(this.sourceDirectory);
    }
}
