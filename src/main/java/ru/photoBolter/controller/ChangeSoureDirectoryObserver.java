package ru.photoBolter.controller;

import java.nio.file.Path;

public class ChangeSoureDirectoryObserver extends AbstractChangeDirectoryObserver {

    private final ChangeSourceDirectoryObservable observed;

    public ChangeSoureDirectoryObserver(ChangeSourceDirectoryObservable observed) {
        this.observed = observed;
    }

    @Override
    public void changeDirectory(Path path) {
        observed.changeSourceDirectory(path);
    }
}
