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
        while(true) {
            try {
                outputView.printBoard(board);

                Position selectedPiecePosition = inputView.selectPiece();
                List<Position> positions = board.computeReachableDestination(selectedPiecePosition);

                outputView.printReachableDestinations(positions);
                Position destination = inputView.askMovableDestination();
                Piece piece = board.moveOrCatchPiece(selectedPiecePosition, destination);
                outputView.printMoveResult(piece);
            } catch (IllegalArgumentException | IllegalStateException e) {
                outputView.printExceptionMessage(e);
            }
        }
    }
}
