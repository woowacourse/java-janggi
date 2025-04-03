package janggi;

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
        DataController dataController = new DataController(new JanggiDao(), new Viewer());
        GameController gameController = setGameController(dataController);

        gameController.start();
        gameController.finish();
    }

    private static GameController setGameController(DataController dataController) {
        if (dataController.hasExistingGame()) {
            List<PieceDto> pieceDtos = dataController.loadPieces();
            return setLoadedGame(dataController, pieceDtos);
        }
        return setNewGame(dataController);
    }

    private static GameController setNewGame(DataController dataController) {
        Map<Position, Piece> initialPieces = PieceFactory.initialize();
        Viewer viewer = new Viewer();
        Board board = new Board(initialPieces);
        JanggiGame janggiGame = new JanggiGame(board, Side.CHO);

        dataController.saveNewPieces(initialPieces);

        return new GameController(dataController, viewer, janggiGame);
    }

    private static GameController setLoadedGame(DataController dataController, List<PieceDto> loadedData) {
        Viewer viewer = new Viewer();
        Board board = new Board(dataController.convertFromDto(loadedData));
        Side currentTurn = dataController.loadCurrentTurn();
        JanggiGame janggiGame = new JanggiGame(board, currentTurn);

        return new GameController(dataController, viewer, janggiGame);
    }
}
