package ru.photoBolter.model;

import ru.photoBolter.controller.ChangeCurrentFileObservable;
import ru.photoBolter.controller.ChangeCurrentFileObserver;
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
    private ChangeCurrentFileObserver changeCurrentFileObserver;
    private PropertiesSaver propertiesSaver;

    public void setPropertiesSaver(PropertiesSaver propertiesSaver) {
        this.propertiesSaver = propertiesSaver;
    }

    public void setChangeSoureDirectoryObserver(ChangeSoureDirectoryObserver changeSoureDirectoryObserver) {
        this.changeSoureDirectoryObserver = changeSoureDirectoryObserver;
    }

    public void setChangeCurrentFileObserver(ChangeCurrentFileObserver changeCurrentFileObserver) {
        this.changeCurrentFileObserver = changeCurrentFileObserver;
    }

    private ChangeSoureDirectoryObserver changeSoureDirectoryObserver;

    public Path getSourceDirectory() {
        return this.sourceDirectory;
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
        if (checkJpgFile(currentFile)) {
            changeCurrentFileObserver.changeCurrentFile(currentFile);
        }
    }

    private boolean checkJpgFile(Path currentFile) {
        String stringFileName = currentFile.getName(currentFile.getNameCount() - 1).toString();
        return stringFileName.toLowerCase().contains(".jpg");
    }
}
