package ru.photoBolter.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.photoBolter.controller.ChangeSourceDirectoryObservable;
import ru.photoBolter.model.PathContainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public class FileTreeView implements ChangeSourceDirectoryObservable {
    private Logger logger = Logger.getLogger(FileTreeView.class.getName());
    private final Node folderIcon = new ImageView(
            new Image(getClass().getResourceAsStream("../../../folder.png"))
    );

    private TreeView<PathContainer> tree;

    public FileTreeView() {
        tree = new TreeView();
        tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<PathContainer>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<PathContainer>> observable, TreeItem<PathContainer> oldValue, TreeItem<PathContainer> newValue) {
                logger.info(newValue.getValue().toString());
            }
        });
    }

    public Node getView() {
        return tree;
    }

    @Override
    public void changeSourceDirectory(Path path) {
        TreeItem<PathContainer> rootItem = new TreeItem(
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
        ImageView imageView = null;
        if (Files.isDirectory(filePath)) {
            imageView = new ImageView(
                    new Image(getClass().getResourceAsStream("../../../folder.png"))
            );
        } else {
            imageView = new ImageView(
                    new Image(getClass().getResourceAsStream("../../../file.png"))
            );
        }
        return imageView;
    }
}
