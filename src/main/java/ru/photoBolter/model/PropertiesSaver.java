package ru.photoBolter.model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static ru.photoBolter.Constants.FILE_NAME;
import static ru.photoBolter.Constants.SOURCE_DIRECTORY;

public class PropertiesSaver {

    private final Model model;

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

    public PropertiesSaver(Model model) {
        this.model = model;
    }

    public void save() {
        prop.setProperty(SOURCE_DIRECTORY, model.getSourceDirectory().toString());

        try {
            prop.store(outputStream, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
