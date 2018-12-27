package ru.photoBolter.controller;

import ru.photoBolter.controller.observable.ChangeCurrentFileObservable;

import java.nio.file.Path;

public class ChangeCurrentFileObserver {

    private final ChangeCurrentFileObservable observable;

    public ChangeCurrentFileObserver(ChangeCurrentFileObservable observable) {
        this.observable = observable;
    }

    public void changeCurrentFile(Path path) {
        this.observable.changeCurrentFile(path);
    }
}
