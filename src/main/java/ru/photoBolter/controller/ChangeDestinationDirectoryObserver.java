package ru.photoBolter.controller;

import ru.photoBolter.controller.observable.ChangeDestinationDirectoryObservable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ChangeDestinationDirectoryObserver extends AbstractChangeDirectoryObserver {

    private final List<ChangeDestinationDirectoryObservable> observedList = new ArrayList<>();

    public List<ChangeDestinationDirectoryObservable> getObservedList() {
        return observedList;
    }

    @Override
    public void changeDirectory(Path path) {
        observedList.stream().forEach(observable -> {
            observable.changeDestinationDirectory(path);
        });
    }
}
