package ru.photoBolter.controller;

import ru.photoBolter.controller.observable.StatusObserverable;
import ru.photoBolter.model.PathContainer;

public class StatusObserver {

    private StatusObserverable statusObserverable;

    public void setStatusObserverable(StatusObserverable statusObserverable) {
        this.statusObserverable = statusObserverable;
    }

    public void changeStatus(PathContainer pathContainer) {
        statusObserverable.changeStatus(pathContainer);
    }
}
