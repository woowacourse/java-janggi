package janggi;

import janggi.manager.JanggiGame;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();
        final JanggiGame janggiGame = new JanggiGame(inputView, outputView);
        janggiGame.start();
    }
}
