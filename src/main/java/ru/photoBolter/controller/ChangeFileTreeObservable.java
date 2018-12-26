package ru.photoBolter.controller;

import ru.photoBolter.model.PathContainer;

import java.util.List;

public interface ChangeFileTreeObservable {
    void changeFileTree(String sourceDirectoryName, List<PathContainer> pathContainers);
}
