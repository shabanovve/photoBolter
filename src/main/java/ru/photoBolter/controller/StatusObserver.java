package ru.photoBolter.controller;

import java.nio.file.Path;

public class StatusObserver {

    private StatusObserverable statusObserverable;

    public void setStatusObserverable(StatusObserverable statusObserverable) {
        this.statusObserverable = statusObserverable;
    }

    public void changeStatus(Path path, boolean copied) {
        statusObserverable.changeStatus(path, copied);
    }
}
