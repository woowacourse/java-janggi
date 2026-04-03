package controller;

import exception.JanggiGameException;

@FunctionalInterface
interface ExecutableTask {
    void execute() throws JanggiGameException;
}
