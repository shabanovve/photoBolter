package ru.photoBolter.controller;

import java.nio.file.Path;
import java.util.List;

public class ChangeDestinationDirectoryObserver extends AbstractChangeDirectoryObserver {

    private final List<ChangeSourceDirectoryObservable> observedList;

    public ChangeDestinationDirectoryObserver(List<ChangeSourceDirectoryObservable> observedList) {
        this.observedList = observedList;
    }

    @Override
    public void changeDirectory(Path path) {
        observedList.stream().forEach(observable -> {
            observable.changeSourceDirectory(path);
        });
    }
}
