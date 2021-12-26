package com.example.undoredo2021fx;

import java.util.ArrayList;
import java.util.Stack;

public class InteractionModel {
    Box selection;
    ArrayList<BoxModelListener> subscribers;
    ArrayList<StackListener> stackSubscribers;
    Stack<BoxCommand> undoStack, redoStack;

    public InteractionModel() {
        subscribers = new ArrayList<>();
        stackSubscribers = new ArrayList<>();
        selection = null;
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    public void setSelection(Box b) {
        selection = b;
        notifySubscribers();
    }

    public void addSubscriber (BoxModelListener aSub) {
        subscribers.add(aSub);
    }
    public void addStackSubscriber (StackListener aSub) {
        stackSubscribers.add(aSub);
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }

    private void notifyStackSubscribers() {
        stackSubscribers.forEach(sub -> {
            sub.stackChanged(undoStack, "Undo");
            sub.stackChanged(redoStack, "Redo");
        });
    }

    public void handleUndo() {
        if (undoStack.empty()) {
            System.out.println("Nothing more to undo!");
        } else {
            BoxCommand bc = undoStack.pop();
            bc.undo();
            redoStack.push(bc);
            notifyStackSubscribers();
        }
    }

    public void handleRedo() {
        if (redoStack.empty()) {
            System.out.println("Nothing more to redo!");
        } else {
            BoxCommand bc = redoStack.pop();
            bc.doIt();
            undoStack.push(bc);
            notifyStackSubscribers();
        }
    }

    public void addToUndoStack(BoxCommand bc) {
        undoStack.push(bc);
        notifyStackSubscribers();
    }
}
