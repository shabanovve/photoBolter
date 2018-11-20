package ru.photoBolter.controller;

import java.nio.file.Path;
import java.util.List;

public class ChangeSoureDirectoryObserver extends AbstractChangeDirectoryObserver {

    private final List<ChangeSourceDirectoryObservable> observedList;

    public ChangeSoureDirectoryObserver(List<ChangeSourceDirectoryObservable> observedList) {
        this.observedList = observedList;
    }

    @Override
    public void changeDirectory(Path path) {
        observedList.stream().forEach(observable -> {
            observable.changeSourceDirectory(path);
        });
    }
}
