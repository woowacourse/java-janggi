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
        while (true) {
            try {
                outputView.printBoard(board);

                Position selectedPiecePosition = inputView.selectPiece();

                List<Position> reachablePositions = computeReachableDestinations(selectedPiecePosition);

                Piece catchedPiece = processMove(selectedPiecePosition, reachablePositions);

                if (board.checkGameIsOver(catchedPiece)) {
                    break;
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                outputView.printExceptionMessage(e);
            }
        }
    }

    private List<Position> computeReachableDestinations(final Position selectedPiecePosition) {
        List<Position> reachablePositions = board.computeReachableDestination(selectedPiecePosition);
        outputView.printReachableDestinations(reachablePositions);
        return reachablePositions;
    }

    private Piece processMove(final Position selectedPiecePosition, final List<Position> reachablePositions) {
        Position destination = inputView.askMovableDestination();
        Piece catchedPiece = board.moveOrCatchPiece(selectedPiecePosition, destination, reachablePositions);
        outputView.printMoveResult(catchedPiece);
        return catchedPiece;
    }

}
