package ru.photoBolter.controller.observer;


import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ru.photoBolter.exception.CopyFileExeption;
import ru.photoBolter.exception.MetadataException;
import ru.photoBolter.model.FileService;
import ru.photoBolter.model.FileStatus;
import ru.photoBolter.model.Model;
import ru.photoBolter.model.PathContainer;
import ru.photoBolter.util.FilePathHelper;

import java.util.logging.Logger;

import static ru.photoBolter.model.FileStatus.COPIED;
import static ru.photoBolter.model.FileStatus.WRONG;
import static ru.photoBolter.util.FilePathHelper.checkCopy;

public class PressEnterHandler implements EventHandler<KeyEvent> {
    private Logger logger = Logger.getLogger(PressEnterHandler.class.getName());
    private final Model model;
    private final StatusObserver statusObserver;
    private final FileService fileService;

    public PressEnterHandler(Model model, StatusObserver statusObserver, FileService fileService) {
        this.model = model;
        this.statusObserver = statusObserver;
        this.fileService = fileService;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            boolean notDirectory = !model.getCurrentFile().getPath().toFile().isDirectory();
            if (notDirectory) {
                try {
                    fileService.copyFile(model.getCurrentFile(), model.getDestinationDirectory());
                } catch (CopyFileExeption e) {
                    logger.warning("CopyFileExeption!");
                    PathContainer pathContainer = correctStatus(WRONG);
                    statusObserver.changeStatus(pathContainer);
                }

                boolean copied = false;
                try {
                    copied = checkCopy(model.getCurrentFile(), model.getDestinationDirectory());
                } catch (MetadataException e) {
                    logger.warning("CopyFileExeption!");
                }
                PathContainer pathContainer = correctStatus(copied ? COPIED : WRONG);
                statusObserver.changeStatus(pathContainer);
            }
            logger.info("Pressed " + keyEvent.getCode().getName());
        }

    }

    private PathContainer correctStatus(FileStatus status) {
        PathContainer pathContainer = FilePathHelper.getPathContainer(
                model.getCurrentFile().getPath(),
                model.getPathContainerList()
        );
        pathContainer.setStatus(status);
        return pathContainer;
    }
}
