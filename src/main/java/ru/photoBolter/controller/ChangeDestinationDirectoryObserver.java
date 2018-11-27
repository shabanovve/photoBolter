package ru.photoBolter.controller;

import java.nio.file.Path;
import java.util.List;

public class ChangeDestinationDirectoryObserver extends AbstractChangeDirectoryObserver {

    private final List<ChangeDestinationDirectoryObservable> observedList;

    public ChangeDestinationDirectoryObserver(List<ChangeDestinationDirectoryObservable> observedList) {
        this.observedList = observedList;
    }

    @Override
    public void changeDirectory(Path path) {
        observedList.stream().forEach(observable -> {
            observable.changeDestinationDirectory(path);
        });
    }
}
