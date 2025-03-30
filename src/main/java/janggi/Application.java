package janggi;

import janggi.dao.GameDao;
import janggi.game.JanggiGame;
import janggi.view.GameInputOutput;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        GameInputOutput gameInputOutput = new GameInputOutput(new InputView(), new OutputView());
        JanggiGame janggiGame = new JanggiGame(gameInputOutput, new GameDao());
        janggiGame.start();
    }
}
