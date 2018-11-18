package ru.photoBolter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.photoBolter.view.FileTreeView;
import ru.photoBolter.view.SourceDirectoryChooser;

import java.io.File;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(final Stage primaryStage) {
        VBox root = new VBox();
        root.getChildren().add(
                new SourceDirectoryChooser(new File("/"))
                        .getOpenButton()
        );
        root.getChildren().add(
                new FileTreeView()
                        .getView()
        );
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

}
