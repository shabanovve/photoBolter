package ru.photoBolter.model;

import ru.photoBolter.controller.observable.ChangeCurrentFileObservable;
import ru.photoBolter.controller.observable.ChangeDestinationDirectoryObservable;
import ru.photoBolter.controller.observable.ChangeSourceDirectoryObservable;
import ru.photoBolter.controller.observer.ChangeDestinationDirectoryModelObserver;
import ru.photoBolter.controller.observer.ChangeFileTreeObserver;
import ru.photoBolter.controller.observer.ChangeModelCurrentFileObserver;
import ru.photoBolter.util.FilePathHelper;

import java.nio.file.Path;
import java.util.List;

import static ru.photoBolter.util.FilePathHelper.getName;

public class Model implements ChangeSourceDirectoryObservable, ChangeCurrentFileObservable, ChangeDestinationDirectoryObservable
{

    private Path sourceDirectory;
    private Path destinationDirectory;
    private PathContainer currentFile;
    private PropertiesSaver propertiesSaver;
    private ChangeFileTreeObserver changeFileTreeObserver;
    private FileService fileService;
    private List<PathContainer> pathContainerList;
    private ChangeModelCurrentFileObserver changeModelCurrentFileObserver ;
    private ChangeDestinationDirectoryModelObserver changeDestinationDirectoryModelObserver;

    public void setPathContainerList(List<PathContainer> pathContainerList) {
        this.pathContainerList = pathContainerList;
    }

    public List<PathContainer> getPathContainerList() {
        return pathContainerList;
    }

    public void setChangeFileTreeObserver(ChangeFileTreeObserver changeFileTreeObserver) {
        this.changeFileTreeObserver = changeFileTreeObserver;
    }

    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    public void setPropertiesSaver(PropertiesSaver propertiesSaver) {
        this.propertiesSaver = propertiesSaver;
    }

    public void setSourceDirectory(Path sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }

    public void setDestinationDirectory(Path destinationDirectory) {
        if (destinationDirectory != null) {
            this.destinationDirectory = destinationDirectory;
        }
    }

    public PathContainer getCurrentFile() {
        return currentFile;
    }

    public Path getSourceDirectory() {
        return this.sourceDirectory;
    }

    public Path getDestinationDirectory() {
        return destinationDirectory;
    }

    public void setChangeModelCurrentFileObservable(ChangeModelCurrentFileObserver changeModelCurrentFileObserver) {
        this.changeModelCurrentFileObserver = changeModelCurrentFileObserver;
    }

    public void setChangeDestinationDirectoryModelObserver(ChangeDestinationDirectoryModelObserver changeDestinationDirectoryModelObserver) {
        this.changeDestinationDirectoryModelObserver = changeDestinationDirectoryModelObserver;
    }

    @Override
    public void changeSourceDirectory(Path path) {
        if (path.equals(this.sourceDirectory)) {
            return;
        }
        this.sourceDirectory = path;
        pathContainerList = fileService.createFileList(sourceDirectory);
        fileService.defineStatus(pathContainerList, destinationDirectory);
        changeFileTreeObserver.changeFileTree(getName(this.sourceDirectory), this.pathContainerList);
        if (propertiesSaver != null) {
            propertiesSaver.save(this);
        }
    }

    @Override
    public void changeCurrentFile(Path path) {
        this.currentFile = FilePathHelper.getPathContainer(path, pathContainerList);
        if (checkJpgFile(currentFile.getPath())) {
            changeModelCurrentFileObserver.changeCurrentFile(currentFile);
        }
    }

    private boolean checkJpgFile(Path currentFile) {
        String stringFileName = currentFile.getName(currentFile.getNameCount() - 1).toString();
        return stringFileName.toLowerCase().contains(".jpg");
    }

    public void setCurrentFile(PathContainer currentFile) {
        if (currentFile != null) {
            this.currentFile = currentFile;
        }
    }

    @Override
    public void changeDestinationDirectory(Path path) {
        if (path.equals(this.destinationDirectory)) {
            return;
        }
        this.destinationDirectory = path;
        changeDestinationDirectoryModelObserver.changeDestinationDirectory(this.destinationDirectory.toString());
        if (propertiesSaver != null) {
            propertiesSaver.save(this);
        }

    }
}
