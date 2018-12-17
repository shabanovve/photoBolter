package ru.photoBolter.model;

import ru.photoBolter.controller.*;

import java.nio.file.Path;

public class Model implements ChangeSourceDirectoryObservable, ChangeCurrentFileObservable, ChangeDestinationDirectoryObservable
{

    private Path sourceDirectory;
    private Path destinationDirectory;
    private Path currentFile;
    private ChangeCurrentFileObserver changeCurrentFileObserver;
    private PropertiesSaver propertiesSaver;
    private ChangeDestinationDirectoryObserver changeDestinatonDirectoryObserver;

    public void setPropertiesSaver(PropertiesSaver propertiesSaver) {
        this.propertiesSaver = propertiesSaver;
    }

    public void setChangeSoureDirectoryObserver(ChangeSoureDirectoryObserver changeSoureDirectoryObserver) {
        this.changeSoureDirectoryObserver = changeSoureDirectoryObserver;
    }

    public void setChangeDestinatonDirectoryObserver(ChangeDestinationDirectoryObserver changeDestinatonDirectoryObserver) {
        this.changeDestinatonDirectoryObserver = changeDestinatonDirectoryObserver;
    }

    public void setChangeCurrentFileObserver(ChangeCurrentFileObserver changeCurrentFileObserver) {
        this.changeCurrentFileObserver = changeCurrentFileObserver;
    }

    public void setSourceDirectory(Path sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }

    public void setDestinationDirectory(Path destinationDirectory) {
        if (destinationDirectory != null) {
            this.destinationDirectory = destinationDirectory;
        }
    }

    public Path getCurrentFile() {
        return currentFile;
    }

    private ChangeSoureDirectoryObserver changeSoureDirectoryObserver;

    public Path getSourceDirectory() {
        return this.sourceDirectory;
    }

    public Path getDestinationDirectory() {
        return destinationDirectory;
    }

    @Override
    public void changeSourceDirectory(Path path) {
        if (path.equals(this.sourceDirectory)) {
            return;
        }
        this.sourceDirectory = path;
        changeSoureDirectoryObserver.changeDirectory(this.sourceDirectory);
        if (propertiesSaver != null) {
            propertiesSaver.save(this);
        }
    }

    @Override
    public void changeCurrentFile(Path currentFile) {
        this.currentFile = currentFile;
        if (checkJpgFile(currentFile)) {
            changeCurrentFileObserver.changeCurrentFile(currentFile);
        }
    }

    private boolean checkJpgFile(Path currentFile) {
        String stringFileName = currentFile.getName(currentFile.getNameCount() - 1).toString();
        return stringFileName.toLowerCase().contains(".jpg");
    }

    public void setCurrentFile(Path currentFile) {
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
        changeDestinatonDirectoryObserver.changeDirectory(this.destinationDirectory);
        if (propertiesSaver != null) {
            propertiesSaver.save(this);
        }

    }
}
