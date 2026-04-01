package controller;

@FunctionalInterface
interface ExecutableTask {
    void execute() throws IllegalArgumentException;
}
