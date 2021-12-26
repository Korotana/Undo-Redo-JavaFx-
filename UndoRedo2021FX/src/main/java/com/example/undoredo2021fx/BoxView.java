package com.example.undoredo2021fx;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class BoxView extends Pane implements BoxModelListener {
    Canvas myCanvas;
    GraphicsContext gc;
    double width, height;
    BoxModel model;
    private InteractionModel iModel;

    public BoxView() {
        width = 700;
        height = 700;
        myCanvas = new Canvas(width, height);
        gc = myCanvas.getGraphicsContext2D();
        getChildren().add(myCanvas);
    }

    public void setModel(BoxModel newModel) {
        model = newModel;
    }

    public void setController(BoxController controller) {
        // set up event handling
        myCanvas.setOnMousePressed(e -> controller.handlePressed(e, e.getX() / width, e.getY() / height));
        myCanvas.setOnMouseDragged(e -> controller.handleDragged(e, e.getX() / width, e.getY() / height));
        myCanvas.setOnMouseReleased(e -> controller.handleReleased(e, e.getX() / width, e.getY() / height));
    }

    public void setInteractionModel(InteractionModel newModel) {
        iModel = newModel;
    }

    public void draw() {
        double boxLeft, boxTop, boxWidth, boxHeight;

        gc.clearRect(0, 0, width, height);
        gc.setStroke(Color.BLACK);
        for (Box b : model.boxes) {
            boxLeft = b.left * width;
            boxTop = b.top * height;
            boxWidth = b.width * width;
            boxHeight = b.height * height;
            if (b == iModel.selection) {
                gc.setFill(Color.HOTPINK);
            } else {
                gc.setFill(Color.PURPLE);
            }
            gc.fillRect(boxLeft, boxTop, boxWidth, boxHeight);
            gc.strokeRect(boxLeft, boxTop, boxWidth, boxHeight);
        }
    }

    public void modelChanged() {
        draw();
    }
}
