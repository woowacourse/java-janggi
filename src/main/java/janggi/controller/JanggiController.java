package janggi.controller;

import janggi.JanggiGame;
import janggi.database.JanggiDao;
import janggi.piece.Color;
import janggi.piece.Piece;
import janggi.piece.Pieces;
import janggi.position.Position;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.sql.SQLException;
import java.util.Map;

public class JanggiController {
    private final JanggiDao janggiDao;
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.janggiDao = new JanggiDao();
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        JanggiGame janggiGame = loadJanggiGame();
        while (true) {
            janggiMoveLoop(janggiGame);
        }
    }

    private void janggiMoveLoop(final JanggiGame janggiGame) {
        printBoard(janggiGame.getBoard(), janggiGame.getScore(Color.RED), janggiGame.getScore(Color.BLUE));
        final Position startPosition = Position.from(inputView.inputMoveStartPosition());
        final Position endPosition = Position.from(inputView.inputMoveEndPosition());
        final boolean isMoveSuccess = janggiGame.move(startPosition, endPosition);
        if (isMoveSuccess) {
            boolean isSaveSuccess = saveMoveToDatabase(startPosition, endPosition, janggiGame.getPiece(endPosition));
            outputView.printSaveResult(isSaveSuccess);
        }
        outputView.printMoveResult(isMoveSuccess);
    }

    private boolean saveMoveToDatabase(final Position startPosition, final Position endPosition,
                                       final Piece movedPiece) {
        return janggiDao.deleteByPosition(startPosition) &&
                janggiDao.deleteByPosition(endPosition) &&
                janggiDao.savePiece(endPosition, movedPiece);
    }

    private JanggiGame loadJanggiGame() {
        String isNewGame = inputView.inputIsNewGame();
        if (isNewGame.equalsIgnoreCase("new")) {
            outputView.printNewGame();
            return startNewGame();
        }
        if (isNewGame.equalsIgnoreCase("continue")) {
            outputView.printContinueGame();
            return startContinueGame();
        }
        outputView.printLoadJanngiIllegalInput();
        return startContinueGame();
    }

    private JanggiGame startNewGame() {
        final JanggiGame janggiGame = new JanggiGame();
        janggiDao.deleteAllPiece();
        final boolean isSaveSuccess = janggiDao.saveAllPiece(janggiGame.getBoard());
        if (!isSaveSuccess) {
            outputView.printDatabaseOffline();
        }
        return janggiGame;
    }

    private JanggiGame startContinueGame() {
        try {
            final Pieces savedAllPiece = janggiDao.findAllPiece();
            return new JanggiGame(savedAllPiece);
        } catch (final SQLException e) {
            outputView.printDatabaseLoadError();
            return startNewGame();
        }
    }

    private void printBoard(final Map<Position, Piece> board, final double redScore, final double blueScore) {
        outputView.printBoard(board);
        outputView.printScore(redScore, blueScore);
    }
}
