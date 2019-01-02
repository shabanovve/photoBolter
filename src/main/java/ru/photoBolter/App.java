package ru.photoBolter;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ru.photoBolter.controller.observer.*;
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
        ChangeDestinationDirectoryModelObserver changeDestinationDirectoryModelObserver = new ChangeDestinationDirectoryModelObserver();
        ChangeDestinationDirectoryObserver changeDestinationDirectoryObserver = new ChangeDestinationDirectoryObserver();
        changeDestinationDirectoryObserver.getObservedList().add(model);
        ChangeSoureDirectoryObserver changeSoureDirectoryObserver = new ChangeSoureDirectoryObserver();
        changeSoureDirectoryObserver.setObserved(model);

        ChangeSourceDirectoryModelObserver changeSourceDirectoryModelObserver = new ChangeSourceDirectoryModelObserver();

        VBox leftPanel = createLeftPanel(
                primaryStage,
                changeDestinationDirectoryObserver,
                statusObserver,
                changeSoureDirectoryObserver,
                changeDestinationDirectoryModelObserver,
                changeSourceDirectoryModelObserver
        );

        ChangeModelCurrentFileObserver changeModelCurrentFileObserver = new ChangeModelCurrentFileObserver();
        model.setChangeModelCurrentFileObservable(changeModelCurrentFileObserver);
        model.setChangeSourceDirectoryModelObserver(changeSourceDirectoryModelObserver);
        root.getChildren().add(leftPanel);
        root.getChildren().add(
                createRightPanel(
                        changeDestinationDirectoryModelObserver,
                        changeSourceDirectoryModelObserver,
                        changeModelCurrentFileObserver
                )
        );


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
            ChangeDestinationDirectoryModelObserver changeDestinationDirectoryModelObserver,
            ChangeSourceDirectoryModelObserver changeSourceDirectoryModelObserver,
            ChangeModelCurrentFileObserver changeModelCurrentFileObserver
    ) {
        VBox rightPanel = new VBox();

        rightPanel.getChildren().add(
                createSourceTextField(changeSourceDirectoryModelObserver).getView()
        );

        rightPanel.getChildren().add(
                createDestinationTextField(changeDestinationDirectoryModelObserver).getView()
        );

        DateTextField dateTextField = createDateTextField();
        rightPanel.getChildren().add(
                dateTextField.getView()
        );
        changeModelCurrentFileObserver.getObservedList().add(dateTextField);

        PhotoView photoView = new PhotoView();
        changeModelCurrentFileObserver.getObservedList().add(photoView);
        rightPanel.getChildren().add(
                photoView.getView()
        );
        return rightPanel;
    }

    private DateTextField createDateTextField() {
        return new DateTextField();
    }

    private SourceTextField createSourceTextField(ChangeSourceDirectoryModelObserver changeSourceDirectoryModelObserver ) {
        SourceTextField sourceTextField = new SourceTextField();
        sourceTextField.changeSourceDirectory(model.getSourceDirectory());
        changeSourceDirectoryModelObserver.getObservableList().add(sourceTextField);
        return sourceTextField;
    }

    private DestinationTextField createDestinationTextField(ChangeDestinationDirectoryModelObserver changeDestinationDirectoryModelObserver) {
        DestinationTextField destinationTextField = new DestinationTextField();
        destinationTextField.changeDestinationDirectory(model.getDestinationDirectory().toString());
        changeDestinationDirectoryModelObserver.getObservableList().add(destinationTextField);
        return destinationTextField;
    }

    private VBox createLeftPanel(
            Stage primaryStage,
            ChangeDestinationDirectoryObserver changeDestinationDirectoryObserver,
            StatusObserver statusObserver,
            ChangeSoureDirectoryObserver changeSoureDirectoryObserver,
            ChangeDestinationDirectoryModelObserver changeDestinationDirectoryModelObserver,
            ChangeSourceDirectoryModelObserver changeSourceDirectoryModelObserver
    ) {
        VBox leftPanel = new VBox();

        SourceDirectoryChooser sourceDirectoryChooser = createSourceDirectoryChooser(primaryStage, changeSoureDirectoryObserver);
        leftPanel.getChildren().add(
                sourceDirectoryChooser.getView()
        );
        changeSourceDirectoryModelObserver.getObservableList().add(sourceDirectoryChooser);

        DestinationDirectoryChooser destinationDirectoryChooser = createDestinationDirectoryChooser(primaryStage, changeDestinationDirectoryObserver);
        model.setChangeDestinationDirectoryModelObserver(changeDestinationDirectoryModelObserver);
        leftPanel.getChildren().add(
                destinationDirectoryChooser.getView()
        );

        FileTreeView fileTreeView = new FileTreeView();
        fileTreeView.setChangeCurrentFileObserver(new ChangeCurrentFileObserver(model));
        changeDestinationDirectoryModelObserver.getObservableList().add(fileTreeView);
        fileTreeView.setSourceDirectoryName(getName(model.getSourceDirectory()));
        fileTreeView.setPathContainers(model.getPathContainerList());
        fileTreeView.init();
        model.setFileService(fileService);

        statusObserver.setStatusObserverable(fileTreeView);

        ChangeFileTreeObserver changeFileTreeObserver = new ChangeFileTreeObserver();
        changeFileTreeObserver.getObservableList().add(fileTreeView);
        model.setChangeFileTreeObserver(changeFileTreeObserver);

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
