package ru.photoBolter.controller.observer;

import ru.photoBolter.controller.observable.ChangeDestinationDirectoryModelObservable;

import java.util.ArrayList;
import java.util.List;

public class ChangeDestinationDirectoryModelObserver {
    private List<ChangeDestinationDirectoryModelObservable> observableList = new ArrayList<>();

    public List<ChangeDestinationDirectoryModelObservable> getObservableList() {
        return observableList;
    }

    public void changeDestinationDirectory(String destinationDirectory) {
        observableList.forEach(observable -> observable.changeDestinationDirectory(destinationDirectory));
    }
}
