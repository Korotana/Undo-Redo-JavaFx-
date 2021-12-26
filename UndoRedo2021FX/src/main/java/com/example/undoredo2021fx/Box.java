package com.example.undoredo2021fx;

public class Box {
    double left, top, width, height;

    public Box(double newLeft, double newTop, double newWidth, double newHeight) {
        left = newLeft;
        top = newTop;
        width = newWidth;
        height = newHeight;
    }

    public boolean contains(double x, double y) {
        return x >= left && x <= left+width && y >= top && y <= top+height;
    }

    public void move(double dX, double dY) {
        left += dX;
        top += dY;
    }
}
