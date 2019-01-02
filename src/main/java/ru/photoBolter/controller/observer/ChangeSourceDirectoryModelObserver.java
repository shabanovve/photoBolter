package ru.photoBolter.controller.observer;

import ru.photoBolter.controller.observable.ChangeSourceDirectoryModelObservable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ChangeSourceDirectoryModelObserver {
    private List<ChangeSourceDirectoryModelObservable> observableList= new ArrayList<>();

    public List<ChangeSourceDirectoryModelObservable> getObservableList() {
        return observableList;
    }

    public void changeSourceDirectory(Path path) {
        observableList.forEach(observable -> observable.changeSourceDirectory(path));
    }
}
