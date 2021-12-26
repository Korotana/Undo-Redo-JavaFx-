package com.example.undoredo2021fx;

import java.util.ArrayList;
import java.util.Optional;

public class BoxModel {
    public ArrayList<Box> boxes;
    ArrayList<BoxModelListener> subscribers;

    public BoxModel() {
        subscribers = new ArrayList<>();
        boxes = new ArrayList<>();
    }

    public Box createBox(double left, double top, double width, double height) {
        Box b = new Box(left, top, width, height);
        boxes.add(b);
        notifySubscribers();
        return b;
    }

    public void addBox(Box b) {
        boxes.add(b);
        notifySubscribers();
    }

    public void deleteBox(Box b) {
        boxes.remove(b);
        notifySubscribers();
    }

    public Optional<Box> checkHit(double x, double y) {
        return boxes.stream().filter(b -> b.contains(x, y)).reduce((first, second) -> second);
    }

    public void moveBox(Box b, double dX, double dY) {
        b.move(dX,dY);
        notifySubscribers();
    }

    public void addSubscriber (BoxModelListener aSub) {
        subscribers.add(aSub);
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }
}
