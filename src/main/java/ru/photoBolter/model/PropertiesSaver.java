package ru.photoBolter.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

import static ru.photoBolter.Constants.FILE_NAME;

public class PropertiesSaver {

    private Properties prop = new Properties();

    public void save(Map<String,String> params) {
        params.entrySet().stream().forEach(entry -> {
            prop.setProperty(
                    entry.getKey(),
                    entry.getValue()
            );
        });

        try {
            prop.store(Files.newOutputStream(Paths.get(FILE_NAME)), null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
