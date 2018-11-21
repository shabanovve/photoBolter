package ru.photoBolter.model;

import java.nio.file.Path;

public class PathContainer {
    private final Path path;

    public PathContainer(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    @Override
    public String toString() {
        return path.getName(path.getNameCount() - 1).toString();
    }
}
