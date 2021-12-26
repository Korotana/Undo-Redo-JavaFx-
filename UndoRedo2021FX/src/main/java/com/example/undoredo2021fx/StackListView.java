package com.example.undoredo2021fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.Stack;

public class StackListView extends Pane implements StackListener {
    ObservableList<String> items;
    Button goButton;
    String type;

    public StackListView(String newType) {
        type = newType;
        // lists for the undo / redo stacks
        Label title = new Label(type + "Stack");
        title.setFont(Font.font(20));

        ListView<String> itemList = new ListView<>();
        itemList.setStyle("-fx-font-size: 16");
        itemList.setPrefHeight(600);
        items = FXCollections.observableArrayList("Empty");
        itemList.setItems(items);
        goButton = new Button(type + "!");
        goButton.setFont(Font.font(20));

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(title, itemList, goButton);
        this.getChildren().add(root);
    }

    public void setController(BoxController newController) {
        if (type.equals("Undo")) {
            goButton.setOnAction(newController::handleUndo);
        }
        if (type.equals("Redo")) {
            goButton.setOnAction(newController::handleRedo);
        }
    }

    public void stackChanged(Stack<BoxCommand> stack, String stackType) {
        if (stackType.equals(type)) {
            items.clear();
            stack.forEach(bc -> items.add(bc.toString()));
        }
    }
}
