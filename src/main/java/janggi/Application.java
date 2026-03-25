package janggi;

import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        JanggiRunner janggiRunner = new JanggiRunner(inputView, outputView);
        janggiRunner.execute();
    }
}
