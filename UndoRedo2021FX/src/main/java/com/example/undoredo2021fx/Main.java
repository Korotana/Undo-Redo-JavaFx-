package com.example.undoredo2021fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        HBox root = new HBox();

        // MVC
        BoxView view = new BoxView();
        BoxModel model = new BoxModel();
        BoxController controller = new BoxController();
        InteractionModel iModel = new InteractionModel();
        view.setModel(model);
        view.setInteractionModel(iModel);
        controller.setInteractionModel(iModel);
        controller.setModel(model);
        model.addSubscriber(view);
        iModel.addSubscriber(view);

        StackListView undoView = new StackListView("Undo");
        StackListView redoView = new StackListView("Redo");
        iModel.addStackSubscriber(undoView);
        iModel.addStackSubscriber(redoView);
        undoView.setController(controller);
        redoView.setController(controller);

        root.getChildren().addAll(undoView, view , redoView);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        // need to set keyboard focus *after* stage and scene setup
        view.setController(controller);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
