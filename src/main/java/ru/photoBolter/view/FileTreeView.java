package ru.photoBolter.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.photoBolter.controller.observable.ChangeDestinationDirectoryModelObservable;
import ru.photoBolter.controller.observable.ChangeFileTreeObservable;
import ru.photoBolter.controller.observable.StatusObserverable;
import ru.photoBolter.controller.observer.ChangeCurrentFileObserver;
import ru.photoBolter.exception.UnknownStatusException;
import ru.photoBolter.model.PathContainer;

import java.util.List;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;

public class FileTreeView implements ChangeFileTreeObservable, StatusObserverable, ChangeDestinationDirectoryModelObservable {
    private Logger logger = Logger.getLogger(FileTreeView.class.getName());
    private final Node folderIcon = new ImageView(
            new Image(getClass().getResourceAsStream("/folder.png"))
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
    public void changeFileTree(String sourceDirectoryName, List<PathContainer> pathContainers) {
        init(sourceDirectoryName, pathContainers);
    }

    public void init(String sourceDirectoryName, List<PathContainer> pathContainers) {
        rootItem = new TreeItem(
                sourceDirectoryName,
                folderIcon
        );
        rootItem.setExpanded(true);

        List<TreeItem> treeItems = pathContainers.stream()
                .map(pathContainer -> new TreeItem(
                        pathContainer,
                        getIcon(pathContainer))
                )
                .collect(toList());

        treeItems
                .forEach(item -> {
                    rootItem.getChildren().add(item);
                });

        tree.setRoot(rootItem);
        tree.refresh();
    }

    private ImageView getIcon(PathContainer pathContainer) {
        String iconFileName;
        switch (pathContainer.getStatus()) {
            case FILE: {
                iconFileName = "/file.png";
                break;
            }
            case FOLDER: {
                iconFileName = "/folder.png";
                break;
            }
            case COPIED: {
                iconFileName = "/true.png";
                break;
            }
            case WRONG: {
                iconFileName = "/warning.png";
                break;
            }
            default: {
                throw new UnknownStatusException();
            }
        }
        return new ImageView(
                new Image(
                        getClass()
                                .getResourceAsStream(
                                        iconFileName
                                )
                )
        );
    }

    @Override
    public void changeStatus(PathContainer pathContainer) {
        TreeItem<PathContainer> treeElement = rootItem.getChildren()
                .stream()
                .filter(item -> item.getValue().getPath().equals(pathContainer.getPath()))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
        treeElement.setGraphic(getIcon(pathContainer));
    }

    @Override
    public void changeDestinationDirectory(String destinationDirectory) {
        //todo make reinit
    }
}
