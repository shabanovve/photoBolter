package ru.photoBolter;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ru.photoBolter.controller.ChangeCurrentFileObserver;
import ru.photoBolter.controller.ChangeDestinationDirectoryObserver;
import ru.photoBolter.controller.ChangeSoureDirectoryObserver;
import ru.photoBolter.model.FileService;
import ru.photoBolter.model.Model;
import ru.photoBolter.model.ModelInitializer;
import ru.photoBolter.view.*;

import java.util.Arrays;
import java.util.logging.Logger;

public class App extends Application {

    private Model model = new Model();
    private Logger logger = Logger.getLogger(App.class.getName());
    private final FileService fileService = new FileService();

    public static void main(String[] args) {
        launch(args);
    }

    public void start(final Stage primaryStage) {
        ModelInitializer.init(model);

        HBox root = new HBox();

        ChangeDestinationDirectoryObserver changeDestinationDirectoryObserver = new ChangeDestinationDirectoryObserver();
        changeDestinationDirectoryObserver.getObservedList().add(model);
        VBox leftPanel = createLeftPanel(primaryStage, changeDestinationDirectoryObserver);

        root.getChildren().add(leftPanel);
        root.getChildren().add(createRightPanel(changeDestinationDirectoryObserver));


        primaryStage.setScene(new Scene(root));

        makeMaximizedWindow(primaryStage);

        primaryStage.addEventHandler(KeyEvent.ANY, keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                boolean notDirectory = !model.getCurrentFile().toFile().isDirectory();
                if (notDirectory) {
                    fileService.copyFile(model.getCurrentFile(), model.getDestinationDirectory());
                }
                logger.info("Pressed " + keyEvent.getCode().getName());
            }
        });

        primaryStage.show();
    }

    private VBox createRightPanel(ChangeDestinationDirectoryObserver changeDestinationDirectoryObserver) {
        VBox rightPanel = new VBox();

        rightPanel.getChildren().add(
                createDestinationTextField(changeDestinationDirectoryObserver).getView()
        );

        PhotoView photoView = new PhotoView();
        model.setChangeCurrentFileObserver(
                new ChangeCurrentFileObserver(photoView)
        );
        rightPanel.getChildren().add(
                photoView.getView()
        );
        return rightPanel;
    }

    private DestinationTextField createDestinationTextField(ChangeDestinationDirectoryObserver changeDestinationDirectoryObserver) {
        DestinationTextField destinationTextField = new DestinationTextField();
        destinationTextField.changeDestinationDirectory(model.getDestinationDirectory());
        changeDestinationDirectoryObserver.getObservedList().add(destinationTextField);
        return destinationTextField;
    }

    private VBox createLeftPanel(Stage primaryStage, ChangeDestinationDirectoryObserver changeDestinationDirectoryObserver) {
        VBox leftPanel = new VBox();

        SourceDirectoryChooser sourceDirectoryChooser = createSourceDirectoryChooser(primaryStage);
        leftPanel.getChildren().add(
                sourceDirectoryChooser.getView()
        );

        DestinationDirectoryChooser destinationDirectoryChooser = createDestinationDirectoryChooser(primaryStage, changeDestinationDirectoryObserver);
        model.setChangeDestinatonDirectoryObserver(changeDestinationDirectoryObserver);
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
        return leftPanel;
    }

    private DestinationDirectoryChooser createDestinationDirectoryChooser(
            Stage primaryStage, ChangeDestinationDirectoryObserver observer
    ) {
        DestinationDirectoryChooser destinationDirectoryChooser = new DestinationDirectoryChooser();
        destinationDirectoryChooser.setStage(primaryStage);
        destinationDirectoryChooser.setObserver(observer);
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
