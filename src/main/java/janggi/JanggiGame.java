package janggi;

import janggi.board.JanggiBoard;
import janggi.board.Position;
import janggi.database.dao.BoardDao;
import janggi.piece.Piece;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.constant.UserAction;
import java.util.List;

public class JanggiGame {

    private final InputView inputView;
    private final OutputView outputView;
    private final BoardDao boardDao;
    private JanggiBoard board;

    public JanggiGame(final InputView inputView, final OutputView outputView, final BoardDao boardDao) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.boardDao = boardDao;
        this.board = JanggiBoard.initialize();
    }

    public void play() {
        attemptLoadSavedGame();

        while (board.isGameProgress()) {
            try {
                outputView.printBoard(board);
                outputView.printCurrentBoardStatus(board);

                Position selectedPiecePosition = inputView.selectPiece();
                List<Position> reachablePositions = computeReachableDestinations(selectedPiecePosition);

                Position destination = inputView.askMovableDestination();
                board.checkPieceCanMoveTo(destination, reachablePositions);

                processMove(selectedPiecePosition, destination);
                board.checkGameIsOver();

                saveBoard();
            } catch (IllegalArgumentException | IllegalStateException e) {
                outputView.printExceptionMessage(e);
            }
        }
        outputView.printCompetitionResult(board);
    }

    private void attemptLoadSavedGame() {
        UserAction userAction = inputView.askLoadSavedGame();
        if (userAction == UserAction.YES) {
            board = boardDao.loadBoard();
        }
    }

    private List<Position> computeReachableDestinations(final Position selectedPiecePosition) {
        List<Position> reachablePositions = board.computeReachableDestination(selectedPiecePosition);
        outputView.printReachableDestinations(reachablePositions);
        return reachablePositions;
    }

    private Piece processMove(final Position selectedPiecePosition, final Position destination) {
        Piece catchedPiece = board.moveOrCatchPiece(selectedPiecePosition, destination);
        outputView.printMoveResult(catchedPiece);
        board.passTurnToOpponent();
        return catchedPiece;
    }

    private void saveBoard() {
        boardDao.saveBoard(board);
    }

}
