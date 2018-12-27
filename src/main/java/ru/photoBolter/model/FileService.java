package ru.photoBolter.model;

import ru.photoBolter.exception.CopyFileExeption;
import ru.photoBolter.exception.FileListExecption;
import ru.photoBolter.exception.MetadataException;
import ru.photoBolter.util.FilePathHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;
import static ru.photoBolter.model.FileStatus.COPIED;
import static ru.photoBolter.model.FileStatus.FILE;
import static ru.photoBolter.model.FileStatus.FOLDER;
import static ru.photoBolter.util.FilePathHelper.getDestinationPath;


public class FileService {
    private Logger logger = Logger.getLogger(FileService.class.getName());

    public void copyFile(Path source, Path destinationDirectory) {
        try {
            Path destination = null;
            try {
                destination = getDestinationPath(source, destinationDirectory);
            } catch (RuntimeException e) {
                throw new CopyFileExeption(e);
            }
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

    public void defineStatus(List<PathContainer> pathContainers, Path destinationDirectory) {
        pathContainers.forEach(pathContainer -> {
                    Path path = pathContainer.getPath();

                    if (path.toFile().isDirectory()) {
                        pathContainer.setStatus(FOLDER);
                        return;
                    }

                    boolean copied = false;
                    try {
                        copied = FilePathHelper.checkCopy(path, destinationDirectory);
                    } catch (MetadataException e) {
                        logger.warning(
                                String.format(
                                        "Cannot check copy of the file %s. %s",
                                        path.toString(),
                                        e.getMessage()
                                )
                        );
                        //todo set status WRONG
                    }

                    if (copied) {
                        pathContainer.setStatus(COPIED);
                    } else {
                        pathContainer.setStatus(FILE);
                    }
                }
        );

    }
}
