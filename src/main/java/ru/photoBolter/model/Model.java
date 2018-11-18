package ru.photoBolter.model;

import java.nio.file.Path;

public class Model {

    private Path sourceDirectory;

    public Path getSourceDirectory() {
        return sourceDirectory;
    }

    public void setSourceDirectory(Path sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }
}
