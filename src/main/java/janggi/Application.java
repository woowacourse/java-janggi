package janggi;

import janggi.dao.GameRoom;
import janggi.dao.Piece;
import janggi.db.SQLManager;
import janggi.domain.Game;

public class Application {
    public static void main(String[] args) {
        SQLManager sqlManager = new SQLManager("jdbc:sqlite:src/main/resources/janggi.db");

        GameRoom gameRoom = new GameRoom(sqlManager);
        gameRoom.initTable();

        Piece piece = new Piece(sqlManager);
        piece.initTable();

        JanggiService janggiService = new JanggiService(sqlManager, gameRoom, piece);

        Game game = new Game();

        Runner runner = new Runner(janggiService, game);
        runner.run();

        sqlManager.closeConnection();
    }
}
