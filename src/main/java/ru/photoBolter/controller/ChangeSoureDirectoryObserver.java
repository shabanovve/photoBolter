package ru.photoBolter.controller;

import java.nio.file.Path;

public class ChangeSoureDirectoryObserver extends AbstractChangeDirectoryObserver {

    private final ChangeSourceDirectoryObservable changeSourceDirectoryObservable;

    public ChangeSoureDirectoryObserver(ChangeSourceDirectoryObservable changeSourceDirectoryObservable) {
        this.changeSourceDirectoryObservable = changeSourceDirectoryObservable;
    }

    @Override
    public void changeDirectory(Path path) {
        changeSourceDirectoryObservable.changeSourceDirectory(path);
    }
}
