package ru.photoBolter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.photoBolter.controller.ChangeCurrentFileObserver;
import ru.photoBolter.controller.ChangeSoureDirectoryObserver;
import ru.photoBolter.model.Model;
import ru.photoBolter.model.ModelInitializer;
import ru.photoBolter.view.FileTreeView;
import ru.photoBolter.view.SourceDirectoryChooser;

import java.util.ArrayList;
import java.util.Arrays;

public class App extends Application {

    private Model model = new Model();

    public static void main(String[] args) {
        launch(args);
    }

    public void start(final Stage primaryStage) {
        VBox root = new VBox();
        SourceDirectoryChooser sourceDirectoryChooser = new SourceDirectoryChooser();
        sourceDirectoryChooser.setStage(primaryStage);
        sourceDirectoryChooser.setObserver(new ChangeSoureDirectoryObserver(Arrays.asList(model)));
        root.getChildren().add(
                sourceDirectoryChooser.getView()
        );

        FileTreeView fileTreeView = new FileTreeView();
        fileTreeView.setChangeCurrentFileObserver(new ChangeCurrentFileObserver(model));
        ArrayList observed = new ArrayList();
        observed.add(fileTreeView);
        observed.add(sourceDirectoryChooser);
        model.setChangeSoureDirectoryObserver(
                new ChangeSoureDirectoryObserver(observed)
        );
        root.getChildren().add(
                fileTreeView.getView()
        );

        ModelInitializer.init(model);

        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

}
