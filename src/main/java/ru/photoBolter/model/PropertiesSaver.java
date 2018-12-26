package ru.photoBolter.model;

import ru.photoBolter.exception.SaveConfigException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static ru.photoBolter.Constants.DESTINATION_DIRECTORY;
import static ru.photoBolter.Constants.FILE_NAME;
import static ru.photoBolter.Constants.SOURCE_DIRECTORY;

public class PropertiesSaver {

    private Properties prop = new Properties();

    public void save(Model model) {
        Map<String,String> params = new HashMap<>();
        params.put(SOURCE_DIRECTORY, model.getSourceDirectory().toString());
        params.put(DESTINATION_DIRECTORY, model.getDestinationDirectory().toString());

        params.entrySet().stream().forEach(entry -> {
            prop.setProperty(
                    entry.getKey(),
                    entry.getValue()
            );
        });

        try {
            prop.store(Files.newOutputStream(Paths.get(FILE_NAME)), null);
        } catch (IOException e) {
            throw new SaveConfigException(e);
        }
    }
}
