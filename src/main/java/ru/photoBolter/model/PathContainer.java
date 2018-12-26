package ru.photoBolter.model;

import java.nio.file.Path;

public class PathContainer {
    private final Path path;
    private boolean copied;

    public PathContainer(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    public boolean isCopied() {
        return copied;
    }

    public void setCopied(boolean copied) {
        this.copied = copied;
    }

    @Override
    public String toString() {
        return path.getName(path.getNameCount() - 1).toString();
    }
}
