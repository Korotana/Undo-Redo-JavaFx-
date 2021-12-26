package com.example.undoredo2021fx;

import java.text.DecimalFormat;

public class CreateCommand implements BoxCommand {
    Box myBox;
    BoxModel model;
    double boxX, boxY;
    DecimalFormat df;

    public CreateCommand(BoxModel newModel, double newX, double newY) {
        model = newModel;
        myBox = null;
        boxX = newX;
        boxY = newY;
        df = new DecimalFormat("#.##");
    }
    
    public void doIt() {
        if (myBox == null) {
            myBox = model.createBox(boxX - 0.05, boxY - 0.05, 0.1, 0.1);
        } else {
            model.addBox(myBox);
        }
    }
    
    public void undo() {
        model.deleteBox(myBox);
    }
    
    public String toString() {
        return "Create: " + df.format(boxX) + ", " + df.format(boxY);
    }
}
