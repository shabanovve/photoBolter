package ru.photoBolter.model;

import ru.photoBolter.exception.NoConfigException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static ru.photoBolter.Constants.FILE_NAME;
import static ru.photoBolter.Constants.SOURCE_DIRECTORY;

public class PropertiesLoader {

    public void load(Model model) throws NoConfigException {
        if (model == null) {
            throw new RuntimeException("model cannot be null");
        }

        Path configFile = findConfigFile();
        Properties appProps = loadPropertiesFromConfigFile(configFile);
        fillModel(model, appProps);
    }

    private void fillModel(Model model, Properties appProps) {
        if (appProps.getProperty(SOURCE_DIRECTORY) != null) {
            model.setSourceDirectory(
                    Paths.get(appProps.getProperty(SOURCE_DIRECTORY))
            );
        }
    }

    private Properties loadPropertiesFromConfigFile(Path configFile) throws NoConfigException {
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(configFile.toFile()));
        } catch (FileNotFoundException e) {
            throw new NoConfigException(e);
        } catch (IOException e) {
            throw new NoConfigException(e);
        }
        return appProps;
    }

    private Path findConfigFile() throws NoConfigException {
        try {
            return Files.list(Paths.get(System.getProperty("user.dir")))
                    .filter(path -> path.toString().contains(FILE_NAME))
                    .findFirst()
                    .orElseThrow(() -> new NoConfigException("No config file, sorry"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
