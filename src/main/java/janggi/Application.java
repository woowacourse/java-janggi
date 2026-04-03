package janggi;

import janggi.view.output.ConsoleOutputView;
import janggi.view.input.ConsoleInputView;
import janggi.view.input.InputView;
import janggi.view.output.OutputView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new ConsoleInputView();
        OutputView outputView = new ConsoleOutputView();
        JanggiRunner janggiRunner = new JanggiRunner(inputView, outputView);
        janggiRunner.execute();
    }
}
