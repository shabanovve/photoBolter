package ru.photoBolter.model;

import java.nio.file.Path;
import java.time.LocalDate;

public class PathContainer {
    private final Path path;
    private FileStatus status;
    private LocalDate createDate;
    private String sufix;

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

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getSufix() {
        return sufix;
    }

    public void setSufix(String sufix) {
        this.sufix = sufix;
    }

    @Override
    public String toString() {
        return path.getName(path.getNameCount() - 1).toString();
    }
}
