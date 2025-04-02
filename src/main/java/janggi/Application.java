package janggi;

import janggi.dao.GameInformationDao;
import janggi.dao.MovePieceCommandDao;
import janggi.db.DBConnector;
import janggi.db.DBConnectorImpl;
import janggi.game.JanggiGame;
import janggi.view.GameInputOutput;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        GameInputOutput gameInputOutput = new GameInputOutput(new InputView(), new OutputView());
        DBConnector DBConnector = new DBConnectorImpl();
        GameInformationDao gameInformationDao = new GameInformationDao(DBConnector);
        MovePieceCommandDao movePieceCommandDao = new MovePieceCommandDao(DBConnector);
        JanggiGame janggiGame = new JanggiGame(gameInputOutput, gameInformationDao, movePieceCommandDao);
        janggiGame.start();
    }
}
