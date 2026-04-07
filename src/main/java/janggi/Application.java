package janggi;

import janggi.controller.ConsoleController;
import java.util.Scanner;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {
    public static void main(String[] args) {
        ConsoleController consoleController = new ConsoleController(
                new InputView(new Scanner(System.in)),
                new OutputView()
        );
        consoleController.play();
    }
}
