package ru.photoBolter.model;

import java.nio.file.Path;

public class PathContainer {
    private final Path path;
    private FileStatus status;

    public PathContainer(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    public FileStatus getStatus() {
        return status;
    }

    public void setStatus(FileStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return path.getName(path.getNameCount() - 1).toString();
    }
}
