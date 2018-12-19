package ru.photoBolter.controller;

import java.nio.file.Path;

public interface StatusObserverable {

    void changeStatus(Path path, boolean copied);
}
