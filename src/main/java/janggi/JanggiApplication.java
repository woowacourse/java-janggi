package janggi;

import janggi.view.InputView;
import janggi.view.ResultView;

public class JanggiApplication {
    public static void main(final String[] args) {
        final InputView inputView = new InputView();
        final ResultView resultView = new ResultView();
        final JanggiConsole janggiConsole = new JanggiConsole(inputView, resultView);
        janggiConsole.start();
    }
}
