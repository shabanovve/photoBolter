package ru.photoBolter.controller.observer;

import ru.photoBolter.controller.observable.ChangeFileTreeObservable;
import ru.photoBolter.model.PathContainer;

import java.util.ArrayList;
import java.util.List;

public class ChangeFileTreeObserver {
    private final List<ChangeFileTreeObservable> observableList = new ArrayList<>();

    public List<ChangeFileTreeObservable> getObservableList() {
        return observableList;
    }

    public void changeFileTree(String sourceDirectoryName, List<PathContainer> pathContainers) {
        observableList.forEach(observable -> observable.changeFileTree(sourceDirectoryName, pathContainers));
    }
}
