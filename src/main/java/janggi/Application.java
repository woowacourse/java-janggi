package janggi;

import janggi.dao.GameInformationDao;
import janggi.dao.MovePieceCommandDao;
import janggi.game.JanggiGame;
import janggi.view.GameInputOutput;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        GameInputOutput gameInputOutput = new GameInputOutput(new InputView(), new OutputView());
        JanggiGame janggiGame = new JanggiGame(gameInputOutput, new GameInformationDao(), new MovePieceCommandDao());
        janggiGame.start();
    }
}
