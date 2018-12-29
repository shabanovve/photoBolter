package ru.photoBolter.model;

import ru.photoBolter.exception.*;
import ru.photoBolter.util.FilePathHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;
import static ru.photoBolter.model.FileStatus.*;
import static ru.photoBolter.util.FilePathHelper.createSufix;
import static ru.photoBolter.util.FilePathHelper.getName;


public class FileService {
    private Logger logger = Logger.getLogger(FileService.class.getName());

    public void copyFile(PathContainer pathContainer, Path destinationDirectory) {
        try {
            Path destination = Paths.get(
                    destinationDirectory
                            .toString()
                            .concat(pathContainer.getSufix())
            );

            if (!destination.toFile().exists()) {
                destination.toFile().mkdirs();
            }
            Files.copy(
                    pathContainer.getPath(),
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
        for (PathContainer pathContainer : pathContainers) {
            Path path = pathContainer.getPath();

            if (path.toFile().isDirectory()) {
                pathContainer.setStatus(FOLDER);
                continue;
            }

            boolean copied = false;
            try {
                copied = FilePathHelper.checkCopy(pathContainer, destinationDirectory);
            } catch (MetadataException | NoDirectoryInJpgException | NoDateInJpgException | ValidateDateException e) {
                logger.warning(
                        String.format(
                                "Cannot check copy of the file %s. %s",
                                path.toString(),
                                e.getMessage()
                        )
                );
                pathContainer.setStatus(WRONG);
                continue;
            }

            pathContainer.setStatus(copied ? COPIED : FILE);
        }

    }

    public void defineCreateDate(List<PathContainer> fileList) {
        for (PathContainer pathContainer : fileList) {

            LocalDate localDateFromMetadata = null;
            try {
                localDateFromMetadata = FilePathHelper.getLocalDateFromMetadata(pathContainer.getPath());
                pathContainer.setCreateDate(localDateFromMetadata);
            } catch (MetadataException e) {
                logger.warning("MetadataException! " + pathContainer.getPath().toString());
            } catch (NoDirectoryInJpgException e) {
                logger.warning(e.getMessage());
            } catch (NoDateInJpgException e) {
                logger.warning(e.getMessage());
            }
        }

    }

    public void defineSufix(List<PathContainer> fileList) {
        for (PathContainer pathContainer : fileList) {
            if (pathContainer.getCreateDate() == null) {
                continue;
            }
            try {
                pathContainer.setSufix(
                        createSufix(
                                pathContainer.getCreateDate(),
                                getName(pathContainer.getPath())
                        )
                );
            } catch (ValidateDateException e) {
                logger.warning(
                        String.format(
                                "ValidateDateException! %s %s",
                                e.getMessage(),
                                pathContainer.getPath().toString()
                        )
                );
                continue;
            }
        }

    }
}
