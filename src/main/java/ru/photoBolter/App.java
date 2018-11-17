package ru.photoBolter;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class App extends Application {

    private final Node folderIcon = new ImageView(
            new Image(getClass().getResourceAsStream("../../folder.png"))
    );

    private final Node fileIcon = new ImageView(
            new Image(getClass().getResourceAsStream("../../file.png"))
    );

    public static void main(String[] args) {
        launch(args);
    }

    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Tree View Sample");

        final DirectoryChooser directoryChooser = new DirectoryChooser();

        final Button openButton = new Button("Open a Picture...");


        openButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        directoryChooser.showDialog(primaryStage);
                    }
                });



        TreeItem<String> rootItem = new TreeItem<>("Inbox", folderIcon);
        rootItem.setExpanded(true);
        for (int i = 1; i < 6; i++) {
            TreeItem<String> item = new TreeItem<>("Message" + i, fileIcon);
            rootItem.getChildren().add(item);
        }
        TreeView<String> tree = new TreeView<>(rootItem);
        StackPane root = new StackPane();
        root.getChildren().add(tree);
        root.getChildren().add(openButton);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

}
