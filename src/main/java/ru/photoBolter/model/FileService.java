package ru.photoBolter.model;

import ru.photoBolter.exception.CopyFileExeption;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static ru.photoBolter.util.FilePathHelper.getDestinationPath;


public class FileService {

    public void copyFile(Path source, Path destinationDirectory) {
        try {
            Path destination = getDestinationPath(source, destinationDirectory);
            if (!destination.toFile().exists()) {
                destination.toFile().mkdirs();
            }
            Files.copy(
                    source,
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException e) {
            throw new CopyFileExeption(e);
        }
    }


}
