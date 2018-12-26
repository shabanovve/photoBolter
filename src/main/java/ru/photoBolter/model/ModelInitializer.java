package ru.photoBolter.model;

import ru.photoBolter.exception.NoConfigException;
import ru.photoBolter.util.FilePathHelper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class ModelInitializer {
    private static Logger logger = Logger.getLogger(ModelInitializer.class.getName());

    public static void init(Model model, FileService fileService) {
        try {
            model.setPropertiesSaver(new PropertiesSaver());
            new PropertiesLoader().load(model);
        } catch (NoConfigException e) {
            logger.warning(e.getMessage());
        }

        initPaths(model);

        model.setPathContainerList(
                fileService.createFileList(model.getSourceDirectory())
        );

        model.getPathContainerList().forEach(pathContainer -> {
                    pathContainer.setCopied(
                            FilePathHelper.checkCopy(pathContainer.getPath(), model.getDestinationDirectory())
                    );
                }
        );
    }

    private static void initPaths(Model model) {
        Path userDirectory = Paths.get(System.getProperty("user.dir"));
        if (model.getSourceDirectory() == null || !Files.exists(model.getSourceDirectory())) {
            model.setSourceDirectory(userDirectory);
        }

        if (model.getDestinationDirectory() == null || !Files.exists(model.getDestinationDirectory())) {
            model.setDestinationDirectory(userDirectory);
        }

        if (model.getCurrentFile() == null || !Files.exists(model.getCurrentFile())) {
            model.setCurrentFile(userDirectory);
        }
    }
}
