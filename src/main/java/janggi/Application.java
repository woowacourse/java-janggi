package janggi;

import janggi.controller.JanggiGame;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        JanggiGame janggiGame = new JanggiGame(new InputView(), new OutputView());
        janggiGame.runGame();
    }
}
