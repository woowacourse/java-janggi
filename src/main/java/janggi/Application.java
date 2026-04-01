package janggi;

import janggi.view.input.ConsoleInputView;
import janggi.view.input.InputView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new ConsoleInputView();
        JanggiRunner janggiRunner = new JanggiRunner(inputView);
        janggiRunner.execute();
    }
}
