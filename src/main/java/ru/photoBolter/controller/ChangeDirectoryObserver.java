package ru.photoBolter.controller;

import java.nio.file.Path;

public interface ChangeDirectoryObserver {
    void changeDirectory(Path path);
}
