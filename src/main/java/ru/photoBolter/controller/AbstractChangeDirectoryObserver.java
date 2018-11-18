package ru.photoBolter.controller;

import java.nio.file.Path;

public abstract class AbstractChangeDirectoryObserver {
    public abstract void changeDirectory(Path path);
}
