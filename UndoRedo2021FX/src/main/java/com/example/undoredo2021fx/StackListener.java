package com.example.undoredo2021fx;

import java.util.Stack;

public interface StackListener {
    void stackChanged(Stack<BoxCommand> stack, String stackType);
}
