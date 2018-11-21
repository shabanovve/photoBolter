package ru.photoBolter.model;

import ru.photoBolter.controller.ChangeCurrentFileObservable;
import ru.photoBolter.controller.ChangeSourceDirectoryObservable;
import ru.photoBolter.controller.ChangeSoureDirectoryObserver;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static ru.photoBolter.Constants.SOURCE_DIRECTORY;

public class Model implements ChangeSourceDirectoryObservable, ChangeCurrentFileObservable
{

    private Path sourceDirectory;
    private Path currentFile;

    private PropertiesSaver propertiesSaver;

    public void setPropertiesSaver(PropertiesSaver propertiesSaver) {
        this.propertiesSaver = propertiesSaver;
    }

    public void setChangeSoureDirectoryObserver(ChangeSoureDirectoryObserver changeSoureDirectoryObserver) {
        this.changeSoureDirectoryObserver = changeSoureDirectoryObserver;
    }

    private ChangeSoureDirectoryObserver changeSoureDirectoryObserver;

    public Path getSourceDirectory() {
        return this.sourceDirectory;
    }

    public void setSourceDirectory(Path sourceDirectory) {
        changeSourceDirectory(sourceDirectory);
    }

    @Override
    public void changeSourceDirectory(Path path) {
        this.sourceDirectory = path;
        changeSoureDirectoryObserver.changeDirectory(this.sourceDirectory);
        if (propertiesSaver != null) {
            Map<String, String> params = new HashMap<>();
            params.put(SOURCE_DIRECTORY, path.toString());
            propertiesSaver.save(params);
        }
    }

    @Override
    public void changeCurrentFile(Path currentFile) {
        this.currentFile = currentFile;
    }
}
