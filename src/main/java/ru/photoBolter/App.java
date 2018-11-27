package ru.photoBolter;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ru.photoBolter.controller.ChangeCurrentFileObserver;
import ru.photoBolter.controller.ChangeDestinationDirectoryObserver;
import ru.photoBolter.controller.ChangeSoureDirectoryObserver;
import ru.photoBolter.model.Model;
import ru.photoBolter.model.ModelInitializer;
import ru.photoBolter.view.DestinationDirectoryChooser;
import ru.photoBolter.view.FileTreeView;
import ru.photoBolter.view.PhotoView;
import ru.photoBolter.view.SourceDirectoryChooser;

import java.util.ArrayList;
import java.util.Arrays;

public class App extends Application {

    private Model model = new Model();

    public static void main(String[] args) {
        launch(args);
    }

    public void start(final Stage primaryStage) {
        ModelInitializer.init(model);

        HBox root = new HBox();

        VBox leftPanel = new VBox();
        VBox rightPanel = new VBox();

        SourceDirectoryChooser sourceDirectoryChooser = createSourceDirectoryChooser(primaryStage);
        leftPanel.getChildren().add(
                sourceDirectoryChooser.getView()
        );

        DestinationDirectoryChooser destinationDirectoryChooser = createDestinationDirectoryChooser(primaryStage);
        model.setChangeDestinatonDirectoryObserver(
                new ChangeDestinationDirectoryObserver(Arrays.asList(destinationDirectoryChooser))
        );
        leftPanel.getChildren().add(
                destinationDirectoryChooser.getView()
        );

        FileTreeView fileTreeView = new FileTreeView();
        fileTreeView.setChangeCurrentFileObserver(new ChangeCurrentFileObserver(model));
        fileTreeView.init(model.getSourceDirectory());

        model.setChangeSoureDirectoryObserver(
                new ChangeSoureDirectoryObserver(Arrays.asList(fileTreeView, sourceDirectoryChooser))
        );
        leftPanel.getChildren().add(
                fileTreeView.getView()
        );

        PhotoView photoView = new PhotoView();
        model.setChangeCurrentFileObserver(
                new ChangeCurrentFileObserver(photoView)
        );
        rightPanel.getChildren().add(
                photoView.getView()
        );

        root.getChildren().add(leftPanel);
        root.getChildren().add(rightPanel);


        primaryStage.setScene(new Scene(root));

        makeMaximizedWindow(primaryStage);

        primaryStage.show();
    }

    private DestinationDirectoryChooser createDestinationDirectoryChooser(Stage primaryStage) {
        DestinationDirectoryChooser destinationDirectoryChooser = new DestinationDirectoryChooser();
        destinationDirectoryChooser.setStage(primaryStage);
        destinationDirectoryChooser.setObserver(new ChangeDestinationDirectoryObserver(Arrays.asList(model)));
        destinationDirectoryChooser.setInitialDirectory(model.getDestinationDirectory().toFile());
        return destinationDirectoryChooser;
    }

    private SourceDirectoryChooser createSourceDirectoryChooser(Stage primaryStage) {
        SourceDirectoryChooser sourceDirectoryChooser = new SourceDirectoryChooser();
        sourceDirectoryChooser.setStage(primaryStage);
        sourceDirectoryChooser.setObserver(new ChangeSoureDirectoryObserver(Arrays.asList(model)));
        sourceDirectoryChooser.setInitialDirectory(model.getSourceDirectory().toFile());
        return sourceDirectoryChooser;
    }

    private void makeMaximizedWindow(Stage primaryStage) {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
    }

}
