package janggi;

import janggi.view.InputView;
import janggi.view.ResultView;

public class JanggiApplication {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        ResultView resultView = new ResultView();
        JanggiConsole janggiConsole = new JanggiConsole(inputView, resultView);
        janggiConsole.start();
    }
}
