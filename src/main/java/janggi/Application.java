package janggi;

import janggi.game.JanggiGame;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        JanggiGame janggiGame = new JanggiGame(new InputView(), new OutputView());
        janggiGame.start();
    }
}
