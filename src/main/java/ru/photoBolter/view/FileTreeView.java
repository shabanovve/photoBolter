package ru.photoBolter.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.photoBolter.controller.ChangeCurrentFileObserver;
import ru.photoBolter.controller.ChangeSourceDirectoryObservable;
import ru.photoBolter.controller.StatusObserverable;
import ru.photoBolter.model.PathContainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public class FileTreeView implements ChangeSourceDirectoryObservable, StatusObserverable {
    private Logger logger = Logger.getLogger(FileTreeView.class.getName());
    private final Node folderIcon = new ImageView(
            new Image(getClass().getResourceAsStream("../../../folder.png"))
    );
    private TreeItem<PathContainer> rootItem;

    private TreeView<PathContainer> tree;
    private ChangeCurrentFileObserver changeCurrentFileObserver;

    public FileTreeView() {
        tree = new TreeView();
        tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<PathContainer>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<PathContainer>> observable, TreeItem<PathContainer> oldValue, TreeItem<PathContainer> newValue) {
                changeCurrentFileObserver.changeCurrentFile(newValue.getValue().getPath());
            }
        });
    }

    public Node getView() {
        return tree;
    }

    public void setChangeCurrentFileObserver(ChangeCurrentFileObserver changeCurrentFileObserver) {
        this.changeCurrentFileObserver = changeCurrentFileObserver;
    }

    @Override
    public void changeSourceDirectory(Path path) {
        init(path);
    }

    public void init(Path path) {
        rootItem = new TreeItem(
                path.getName(path.getNameCount() - 1),
                folderIcon
        );
        rootItem.setExpanded(true);
        try {
            Files.list(path).forEach(filePath -> {
                TreeItem<PathContainer> item = new TreeItem(
                        new PathContainer(filePath),
                        getIcon(filePath)
                );
                rootItem.getChildren().add(item);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        tree.setRoot(rootItem);
        tree.refresh();
    }

    private ImageView getIcon(Path filePath) {
        if (Files.isDirectory(filePath)) {
            return new ImageView(
                    new Image(getClass().getResourceAsStream("../../../folder.png"))
            );
        } else {
            return new ImageView(
                    new Image(getClass().getResourceAsStream("../../../file.png"))
            );
        }
    }

    @Override
    public void changeStatus(Path path, boolean copied) {
        TreeItem<PathContainer> treeElement = rootItem.getChildren()
                .stream()
                .filter(item -> item.getValue().getPath().equals(path))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
        treeElement.setGraphic(getIcon(path, copied));
    }

    private ImageView getIcon(Path path, boolean copied) {
        if (copied) {
            return new ImageView(
                    new Image(getClass().getResourceAsStream("../../../true.png"))
            );
        } else {
            return getIcon(path);
        }
    }
}
