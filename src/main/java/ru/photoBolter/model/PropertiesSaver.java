package ru.photoBolter.model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

import static ru.photoBolter.Constants.FILE_NAME;

public class PropertiesSaver {

    private OutputStream outputStream;

    {
        try {
            outputStream = new FileOutputStream(FILE_NAME);
        } catch (FileNotFoundException e) {
            createEmptyFile();
        }
    }

    private void createEmptyFile() {
        try {
            outputStream = Files.newOutputStream(Paths.get(FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Properties prop = new Properties();

    public void save(Map<String,String> params) {
        params.entrySet().stream().forEach(entry -> {
            prop.setProperty(
                    entry.getKey(),
                    entry.getValue()
            );
        });

        try {
            prop.store(outputStream, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
