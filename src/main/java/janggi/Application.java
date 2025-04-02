package janggi;

import janggi.dao.DaoSettings;
import janggi.dao.JanggiDao;
import janggi.domain.Board;
import janggi.domain.JanggiGame;
import janggi.domain.PieceFactory;
import janggi.domain.Side;
import janggi.domain.movement.Position;
import janggi.domain.piece.Piece;
import janggi.dto.PieceDto;
import janggi.manager.DataController;
import janggi.manager.GameController;
import janggi.view.Viewer;
import java.util.List;
import java.util.Map;

public class Application {

    public static void main(String[] args) {
        DaoSettings daoSettings = new DaoSettings("localhost:13306", "janggi", "root", "root");
        JanggiDao janggiDao = new JanggiDao(daoSettings);
        DataController dataController = new DataController(janggiDao);

        GameController gameController = setGame(dataController);

        gameController.start();
        gameController.finish();
    }

    private static GameController setGame(DataController dataController) {
        List<PieceDto> pieceDtos = dataController.loadFromDatabase();

        if (pieceDtos.isEmpty()) {
            return setNewGame(dataController);
        }
        return loadGame(dataController, pieceDtos);
    }

    private static GameController setNewGame(DataController dataController) {
        Map<Position, Piece> initialPieces = PieceFactory.initialize();
        Viewer viewer = new Viewer();
        Board board = new Board(initialPieces);
        JanggiGame janggiGame = new JanggiGame(board, Side.CHO);

        dataController.setupDatabase(initialPieces);

        return new GameController(dataController, viewer, janggiGame);
    }

    private static GameController loadGame(DataController dataController, List<PieceDto> loadedData) {
        Viewer viewer = new Viewer();
        Board board = new Board(dataController.convertFromDto(loadedData));
        Side currentTurn = dataController.loadCurrentTurn();
        JanggiGame janggiGame = new JanggiGame(board, currentTurn);

        return new GameController(dataController, viewer, janggiGame);
    }
}
