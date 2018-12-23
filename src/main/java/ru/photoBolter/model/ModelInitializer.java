package ru.photoBolter.model;

import ru.photoBolter.exception.NoConfigException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class ModelInitializer {
    private static Logger logger = Logger.getLogger(ModelInitializer.class.getName());

    public static void init(Model model) {
        try {
            model.setPropertiesSaver(new PropertiesSaver());
            new PropertiesLoader().load(model);
        } catch (NoConfigException e) {
            logger.warning(e.getMessage());
        }

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
