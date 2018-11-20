package ru.photoBolter.model;

import ru.photoBolter.exception.NoConfigException;

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

        if (model.getSourceDirectory() == null) {
            model.changeSourceDirectory(Paths.get(System.getProperty("user.dir")));
        }
    }
}
