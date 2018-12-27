package ru.photoBolter.controller.observable;

import java.nio.file.Path;

public interface ChangeCurrentFileObservable {
    void changeCurrentFile(Path currentFile);
}
