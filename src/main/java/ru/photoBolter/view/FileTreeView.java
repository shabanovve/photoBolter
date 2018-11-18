package ru.photoBolter.view;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FileTreeView {
    private final Node folderIcon = new ImageView(
            new Image(getClass().getResourceAsStream("../../../folder.png"))
    );

    private final Node fileIcon = new ImageView(
            new Image(getClass().getResourceAsStream("../../../file.png"))
    );

    private TreeView<String> tree;

    public FileTreeView() {
        TreeItem<String> rootItem = new TreeItem<>("Inbox", folderIcon);
        rootItem.setExpanded(true);
        for (int i = 1; i < 6; i++) {
            TreeItem<String> item = new TreeItem<>("Message" + i, fileIcon);
            rootItem.getChildren().add(item);
        }
        tree = new TreeView<>(rootItem);

    }

    public Node getView() {
        return tree;
    }
}
