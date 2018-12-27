package ru.photoBolter.controller;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ChangeSoureDirectoryObserver extends AbstractChangeDirectoryObserver {

    private final List<ChangeSourceDirectoryObservable> observed = new ArrayList<>();

    public List<ChangeSourceDirectoryObservable> getObservedList() {
        return observed;
    }

    @Override
    public void changeDirectory(Path path) {
        observed.forEach(observable -> observable.changeSourceDirectory(path));
    }
}
