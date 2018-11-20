package ru.photoBolter.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

import static ru.photoBolter.Constants.FILE_NAME;
import static ru.photoBolter.Constants.SOURCE_DIRECTORY;

public class PropertiesLoader {

    public void load(Model model) {
        if (model == null) {
            throw new RuntimeException("model cannot be null");
        }

        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + FILE_NAME;
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(appConfigPath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        model.changeSourceDirectory(
                Paths.get(appProps.getProperty(SOURCE_DIRECTORY))
        );
    }


}
