package ru.photoBolter.view;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.photoBolter.controller.ChangeSourceDirectoryObservable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileTreeView implements ChangeSourceDirectoryObservable {
    private final Node folderIcon = new ImageView(
            new Image(getClass().getResourceAsStream("../../../folder.png"))
    );

    private TreeView<String> tree;

    public FileTreeView() {
        TreeItem<String> rootItem = new TreeItem("Inbox", folderIcon);
        rootItem.setExpanded(true);
        for (int i = 1; i < 6; i++) {
            TreeItem<String> item = new TreeItem(
                    "Message" + i,
                    new ImageView(
                            new Image(getClass().getResourceAsStream("../../../file.png"))
                    )
            );
            rootItem.getChildren().add(item);
        }
        tree = new TreeView<>(rootItem);

    }

    public Node getView() {
        return tree;
    }

    @Override
    public void changeSourceDirectory(Path path) {
        TreeItem<String> rootItem = new TreeItem(
                path.getName(path.getNameCount() - 1),
                folderIcon
        );
        rootItem.setExpanded(true);
        try {
            Files.list(path).forEach(filePath -> {
                TreeItem<String> item = new TreeItem(
                        filePath.getName(filePath.getNameCount() - 1),
                        new ImageView(
                                new Image(getClass().getResourceAsStream("../../../file.png"))
                        )
                );
                rootItem.getChildren().add(item);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        tree.setRoot(rootItem);
        tree.refresh();
    }
}
