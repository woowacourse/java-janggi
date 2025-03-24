package janggi;

import janggi.board.JanggiBoard;
import janggi.board.Position;
import janggi.piece.Piece;
import janggi.piece.Side;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.util.List;

public class JanggiGame {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiGame(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void play() {
        JanggiBoard board = JanggiBoard.initializeWithPieces();
        while (true) {
            for (Side side : Side.getSides()) {
                Piece catchedPiece = playTurn(side, board);
                if (board.checkGameIsOver(catchedPiece)) {
                    outputView.printEndMessage(side);
                    return;
                }
            }
        }
    }

    private Piece playTurn(final Side side, JanggiBoard board) {
        while (true) {
            try {
                outputView.printBoard(board);
                outputView.printTurn(side);
                Position selectedPiecePosition = inputView.selectPiece();

                List<Position> reachableDestinations = computeReachableDestinations(side, board, selectedPiecePosition);
                return processMove(board, selectedPiecePosition, reachableDestinations);
            } catch (IllegalArgumentException | IllegalStateException e) {
                outputView.printExceptionMessage(e);
            }
        }
    }

    private List<Position> computeReachableDestinations(final Side side, final JanggiBoard board, final Position selectedPiecePosition) {
        List<Position> reachablePositions = board.computeReachableDestination(side, selectedPiecePosition);
        outputView.printReachableDestinations(reachablePositions);
        return reachablePositions;
    }

    private Piece processMove(final JanggiBoard board, final Position selectedPiecePosition, final List<Position> reachablePositions) {
        Position destination = inputView.askMovableDestination();
        Piece catchedPiece = board.moveOrCatchPiece(selectedPiecePosition, destination, reachablePositions);
        outputView.printMoveResult(catchedPiece);
        return catchedPiece;
    }

}
