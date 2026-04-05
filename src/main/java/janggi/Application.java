package janggi;

import janggi.dao.GameRoomDao;
import janggi.dao.PieceDao;
import janggi.db.SQLManager;
import janggi.db.TransactionManager;
import janggi.domain.Game;

public class Application {
    public static void main(String[] args) {
        SQLManager sqlManager = new SQLManager("jdbc:sqlite:src/main/resources/janggi.db");
        TransactionManager transactionManager = new TransactionManager(sqlManager);

        GameRoomDao gameRoomDao = new GameRoomDao(sqlManager);
        gameRoomDao.initTable();

        PieceDao pieceDao = new PieceDao(sqlManager);
        pieceDao.initTable();

        JanggiService janggiService = new JanggiService(transactionManager, gameRoomDao, pieceDao);

        Game game = new Game();

        Runner runner = new Runner(janggiService, game);

        int gameId = runner.initBoard();
        runner.runJanggi(gameId);

        sqlManager.closeConnection();
    }
}
