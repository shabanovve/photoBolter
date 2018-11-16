package ru.photoBolter;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        //create tree pane
        VBox treeBox = new VBox();
        treeBox.setPadding(new Insets(10, 10, 10, 10));
        treeBox.setSpacing(10);

        //setup and show the window
        stage.setTitle("JavaFX File Browse Demo");
        StackPane root = new StackPane();
        root.getChildren().addAll(treeBox);
        stage.setScene(new Scene(root, 400, 300));
        stage.show();
    }

}
