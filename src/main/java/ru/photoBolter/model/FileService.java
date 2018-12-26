package ru.photoBolter.model;

import ru.photoBolter.exception.CopyFileExeption;
import ru.photoBolter.exception.FileListExecption;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static java.util.stream.Collectors.toList;
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

    public List<PathContainer> createFileList(Path sourceDirectory) {
        try {
            return Files.list(sourceDirectory)
                    .filter(path -> !path.toFile().isDirectory())
                    .map(path -> new PathContainer(path))
                    .collect(toList());
        } catch (IOException e) {
            throw new FileListExecption(e);
        }
    }
}
