package janggi;

import janggi.board.JanggiBoard;
import janggi.board.Position;
import janggi.piece.Piece;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;

public class JanggiGame {

    private final JanggiBoard board;
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiGame(final InputView inputView, final OutputView outputView) {
        this.board = JanggiBoard.initialize();
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void play() {
        while (board.isGameProgress()) {
            try {
                outputView.printBoard(board);
                outputView.printCurrentBoardStatus(board);

                Position selectedPiecePosition = inputView.selectPiece();
                List<Position> reachablePositions = computeReachableDestinations(selectedPiecePosition);

                Position destination = inputView.askMovableDestination();
                board.checkPieceCanMoveTo(destination, reachablePositions);

                Piece catchedPiece = processMove(selectedPiecePosition, destination);

                board.checkGameIsOver(catchedPiece);
            } catch (IllegalArgumentException | IllegalStateException e) {
                outputView.printExceptionMessage(e);
            }
        }
        outputView.printCompetitionResult(board);
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

}
