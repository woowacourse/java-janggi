package janggi;

import janggi.dao.JanggiDao;
import janggi.domain.Board;
import janggi.domain.PieceFactory;
import janggi.domain.Round;
import janggi.domain.Side;
import janggi.domain.movement.Position;
import janggi.domain.piece.Piece;
import janggi.dto.PieceDto;
import janggi.manager.JanggiGame;
import janggi.view.Viewer;
import java.util.List;
import java.util.Map;

public class Application {

    public static void main(String[] args) {
        JanggiDao janggiDao = new JanggiDao();
        DatabaseController databaseController = new DatabaseController(janggiDao);

        JanggiGame janggiGame = setGame(databaseController);

        janggiGame.start();
    }

    private static JanggiGame setGame(DatabaseController databaseController) {
        List<PieceDto> pieceDtos = databaseController.loadFromDatabase();

        if (pieceDtos.isEmpty()) {
            return setNewGame(databaseController);
        }
        return loadGame(databaseController, pieceDtos);
    }

    private static JanggiGame setNewGame(DatabaseController databaseController) {
        Map<Position, Piece> initialPieces = PieceFactory.initialize();
        Viewer viewer = new Viewer();
        Board board = new Board(initialPieces);
        Round round = new Round(board, Side.CHO);
        databaseController.setupDatabase(initialPieces);
        return new JanggiGame(databaseController, viewer, round);
    }

    private static JanggiGame loadGame(DatabaseController databaseController, List<PieceDto> loadedData) {
        Viewer viewer = new Viewer();
        Board board = new Board(databaseController.convertFromData(loadedData));
        Side currentTurn = databaseController.loadCurrentTurn();
        Round round = new Round(board, currentTurn);
        return new JanggiGame(databaseController, viewer, round);
    }
}
