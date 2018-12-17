package ru.photoBolter.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileService {

    public void copyFile(Path source, Path destinationDirectory) {
        try {
            Path destination = Paths.get(destinationDirectory.toString() + "/" + source.getName(source.getNameCount() - 1));
            Files.copy(
                    source,
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
