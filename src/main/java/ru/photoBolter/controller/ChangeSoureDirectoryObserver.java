package ru.photoBolter.controller;

import ru.photoBolter.model.Model;

import java.nio.file.Path;

public class ChangeSoureDirectoryObserver implements ChangeDirectoryObserver {

    private final Model model;

    public ChangeSoureDirectoryObserver(Model model) {
        this.model = model;
    }

    @Override
    public void changeDirectory(Path path) {
        model.setSourceDirectory(path);
    }
}
