package ru.photoBolter;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ru.photoBolter.controller.*;
import ru.photoBolter.model.FileService;
import ru.photoBolter.model.Model;
import ru.photoBolter.model.ModelInitializer;
import ru.photoBolter.view.*;

import java.util.logging.Logger;

import static ru.photoBolter.util.FilePathHelper.getName;

public class App extends Application {

    private Model model = new Model();
    private Logger logger = Logger.getLogger(App.class.getName());
    private final FileService fileService = new FileService();

    public static void main(String[] args) {
        launch(args);
    }

    public void start(final Stage primaryStage) {
        ModelInitializer.init(model, fileService);

        HBox root = new HBox();

        StatusObserver statusObserver = new StatusObserver();
        ChangeDestinationDirectoryObserver changeDestinationDirectoryObserver = new ChangeDestinationDirectoryObserver();
        changeDestinationDirectoryObserver.getObservedList().add(model);
        ChangeSoureDirectoryObserver changeSoureDirectoryObserver = new ChangeSoureDirectoryObserver();
        changeSoureDirectoryObserver.getObservedList().add(model);
        VBox leftPanel = createLeftPanel(primaryStage, changeDestinationDirectoryObserver, statusObserver, changeSoureDirectoryObserver);

        root.getChildren().add(leftPanel);
        root.getChildren().add(createRightPanel(changeDestinationDirectoryObserver, changeSoureDirectoryObserver));


        primaryStage.setScene(new Scene(root));

        makeMaximizedWindow(primaryStage);

        primaryStage.addEventHandler(KeyEvent.ANY, new PressEnterHandler(
                model,
                statusObserver,
                fileService
        ));

        primaryStage.show();
    }

    private VBox createRightPanel(
            ChangeDestinationDirectoryObserver changeDestinationDirectoryObserver,
            ChangeSoureDirectoryObserver changeSoureDirectoryObserver
    ) {
        VBox rightPanel = new VBox();

        rightPanel.getChildren().add(
                createSourceTextField(changeSoureDirectoryObserver).getView()
        );

        rightPanel.getChildren().add(
                createDestinationTextField(changeDestinationDirectoryObserver).getView()
        );

        rightPanel.getChildren().add(
                createDateTextField()
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

    private TextField createDateTextField() {
        return new DateTextField().getView();
    }

    private SourceTextField createSourceTextField(ChangeSoureDirectoryObserver changeSoureDirectoryObserver) {
        SourceTextField sourceTextField = new SourceTextField();
        sourceTextField.changeSourceDirectory(model.getSourceDirectory());
        changeSoureDirectoryObserver.getObservedList().add(sourceTextField);
        return sourceTextField;
    }

    private DestinationTextField createDestinationTextField(ChangeDestinationDirectoryObserver changeDestinationDirectoryObserver) {
        DestinationTextField destinationTextField = new DestinationTextField();
        destinationTextField.changeDestinationDirectory(model.getDestinationDirectory());
        changeDestinationDirectoryObserver.getObservedList().add(destinationTextField);
        return destinationTextField;
    }

    private VBox createLeftPanel(
            Stage primaryStage,
            ChangeDestinationDirectoryObserver changeDestinationDirectoryObserver,
            StatusObserver statusObserver, ChangeSoureDirectoryObserver changeSoureDirectoryObserver
    ) {
        VBox leftPanel = new VBox();

        SourceDirectoryChooser sourceDirectoryChooser = createSourceDirectoryChooser(primaryStage, changeSoureDirectoryObserver);
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
        fileTreeView.init(
                getName(model.getSourceDirectory()),
                model.getPathContainerList()
        );
        model.setFileService(fileService);

        changeDestinationDirectoryObserver.getObservedList().add(fileTreeView);

        statusObserver.setStatusObserverable(fileTreeView);

        model.setChangeFileTreeObserver(new ChangeFileTreeObserver(fileTreeView));

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

    private SourceDirectoryChooser createSourceDirectoryChooser(Stage primaryStage, ChangeSoureDirectoryObserver changeSoureDirectoryObserver) {
        SourceDirectoryChooser sourceDirectoryChooser = new SourceDirectoryChooser();
        sourceDirectoryChooser.setStage(primaryStage);
        sourceDirectoryChooser.setObserver(changeSoureDirectoryObserver);
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
