package com.example.undoredo2021fx;

import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.Optional;

public class BoxController {
    InteractionModel iModel;
    BoxModel model;
    double prevX, prevY;
    double dragStartX, dragStartY;

    private enum State {
        READY, DRAGGING, PREPARE_CREATE
    }

    private State currentState;

    public BoxController() {
        currentState = State.READY;
    }

    public void setInteractionModel(InteractionModel newModel) {
        iModel = newModel;
    }

    public void setModel(BoxModel newModel) {
        model = newModel;
    }

    public void handlePressed(MouseEvent mouseEvent, double normX, double normY) {
        prevX = normX;
        prevY = normY;

        switch (currentState) {
            case READY:
                // context: are we on a box?
                Optional<Box> hit = model.checkHit(normX, normY);
                if (hit.isPresent()) {
                    // context: on a box
                    // side effects:
                    // - set selection
                    iModel.setSelection(hit.get());
                    // - prepare for summary move command
                    dragStartX = normX;
                    dragStartY = normY;

                    // move to new state
                    currentState = State.DRAGGING;
                } else {
                    // context: we are on the background
                    // side effects: none
                    // move to new state
                    currentState = State.PREPARE_CREATE;
                }
                break;
        }
    }

    public void handleDragged(MouseEvent mouseEvent, double normX, double normY) {
        double dX = normX - prevX;
        double dY = normY - prevY;
        prevX = normX;
        prevY = normY;

        switch (currentState) {
            case DRAGGING:
                // context: none
                model.moveBox(iModel.selection, dX, dY);
                // move to new state: same state
                break;
            case PREPARE_CREATE:
                // context: none
                // side effects: none
                // transition to new state
                currentState = State.READY;
                break;
        }
    }

    public void handleReleased(MouseEvent mouseEvent, double normX, double normY) {
        switch (currentState) {
            case DRAGGING:
                // context: none
                // side effects:
                // create summary move command
                double totalDX = normX - dragStartX;
                double totalDY = normY - dragStartY;
                MoveCommand mc = new MoveCommand(model, iModel.selection, totalDX, totalDY);
                iModel.addToUndoStack(mc);

                // transition to new state
                currentState = State.READY;
                break;
            case PREPARE_CREATE:
                // context: none
                // side effects:
                CreateCommand cc = new CreateCommand(model, normX, normY);
                cc.doIt();
                iModel.addToUndoStack(cc);
                // transition to new state
                currentState = State.READY;
                break;
        }
    }


    public void handleKeyPressed(KeyEvent keyEvent) {
        System.out.println(keyEvent.getCode().toString());
        if (keyEvent.getCode() == KeyCode.DELETE) {
            model.deleteBox(iModel.selection);
        }
    }

    public void handleUndo(ActionEvent event) {
        iModel.handleUndo();
    }

    public void handleRedo(ActionEvent event) {
        iModel.handleRedo();
    }
}
