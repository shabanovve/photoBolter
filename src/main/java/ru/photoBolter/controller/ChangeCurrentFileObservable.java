package ru.photoBolter.controller;

import java.nio.file.Path;

public interface ChangeCurrentFileObservable {
    void changeCurrentFile(Path currentFile);
}
