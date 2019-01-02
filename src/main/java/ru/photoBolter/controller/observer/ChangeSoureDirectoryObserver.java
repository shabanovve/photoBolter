package ru.photoBolter.controller.observer;

import ru.photoBolter.controller.observable.ChangeSourceDirectoryObservable;

import java.nio.file.Path;

public class ChangeSoureDirectoryObserver extends AbstractChangeDirectoryObserver {

    private ChangeSourceDirectoryObservable observed;

    public void setObserved(ChangeSourceDirectoryObservable observed) {
        this.observed = observed;
    }

    @Override
    public void changeDirectory(Path path) {
        observed.changeSourceDirectory(path);
    }
}
