package ru.photoBolter.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.photoBolter.controller.ChangeCurrentFileObserver;
import ru.photoBolter.controller.ChangeDestinationDirectoryObservable;
import ru.photoBolter.controller.ChangeFileTreeObservable;
import ru.photoBolter.controller.StatusObserverable;
import ru.photoBolter.model.PathContainer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;

public class FileTreeView implements ChangeFileTreeObservable, StatusObserverable, ChangeDestinationDirectoryObservable {
    private Logger logger = Logger.getLogger(FileTreeView.class.getName());
    private final Node folderIcon = new ImageView(
            new Image(getClass().getResourceAsStream("/folder.png"))
    );
    private TreeItem<PathContainer> rootItem;

    private TreeView<PathContainer> tree;
    private ChangeCurrentFileObserver changeCurrentFileObserver;
    private Path destination;

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

    public void setDestination(Path destination) {
        this.destination = destination;
    }


    @Override
    public void changeFileTree(String sourceDirectoryName, List<PathContainer> pathContainers) {
        init(sourceDirectoryName, pathContainers);
    }

    public void init(String sourceDirectoryName, List<PathContainer> pathContainers) {
        rootItem = new TreeItem(
                sourceDirectoryName,
                folderIcon
        );

        List<TreeItem> treeItems = pathContainers.stream()
                .map(pathContainer -> new TreeItem(
                        pathContainer,
                        getIcon(pathContainer.getPath()))
                )
                .collect(toList());

        treeItems
                .forEach(item -> {
                    rootItem.getChildren().add(item);
                });

        tree.setRoot(rootItem);
        tree.refresh();
    }

    private ImageView getIcon(Path filePath) {
        if (Files.isDirectory(filePath)) {
            return new ImageView(
                    new Image(getClass().getResourceAsStream("/folder.png"))
            );
        } else {
            return new ImageView(
                    new Image(getClass().getResourceAsStream("/file.png"))
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
                    new Image(getClass().getResourceAsStream("/true.png"))
            );
        } else {
            return getIcon(path);
        }
    }

    @Override
    public void changeDestinationDirectory(Path path) {
        this.destination = path;

        rootItem.getChildren().forEach(treeElement -> {
            treeElement.setGraphic(
                    getIcon(
                            path,
                            false
                    )
            );
        });
    }
}
