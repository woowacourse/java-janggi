package janggi;

import janggi.runner.JanggiGameRunner;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {
    public static void main(String[] args) {
        JanggiGameRunner janggiGameRunner = new JanggiGameRunner(new InputView(), new OutputView());
        janggiGameRunner.play();
    }
}
