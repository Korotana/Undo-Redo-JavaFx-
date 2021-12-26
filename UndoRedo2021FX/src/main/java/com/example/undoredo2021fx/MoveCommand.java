package com.example.undoredo2021fx;

import java.text.DecimalFormat;

public class MoveCommand implements BoxCommand {
    Box myBox;
    BoxModel model;
    double dx, dy;
    DecimalFormat df;

    public MoveCommand(BoxModel newModel, Box newBox, double newDX, double newDY) {
        model = newModel;
        myBox = newBox;
        dx = newDX;
        dy = newDY;
        df = new DecimalFormat("#.##");
    }

    public void doIt() {
        model.moveBox(myBox, dx, dy);
    }

    public void undo() {
        model.moveBox(myBox, dx * -1, dy * -1);
    }

    public String toString() {
        return "Move: " + df.format(dx) + "," + df.format(dy);
    }
}