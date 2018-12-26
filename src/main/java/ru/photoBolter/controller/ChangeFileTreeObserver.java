package ru.photoBolter.controller;

import ru.photoBolter.model.PathContainer;

import java.util.List;

public class ChangeFileTreeObserver {
    private final ChangeFileTreeObservable observable;

    public ChangeFileTreeObserver(ChangeFileTreeObservable observable) {
        this.observable = observable;
    }


    public void changeFileTree(String sourceDirectoryName, List<PathContainer> pathContainers) {
        observable.changeFileTree(sourceDirectoryName, pathContainers);
    }
}
