package janggi;

import janggi.board.Board;
import janggi.board.PieceInitializer;
import janggi.position.Position;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiGame {

    private final OutputView outputView;
    private final InputView inputView;

    public JanggiGame(final OutputView outputView, final InputView inputView) {
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void startGame() {
        Board board = setJanggiBoard();
        while (true) {
            outputView.printJanggiBoard(board.getJanggiBoard());

            try {
                Position presentPosition = readPresentPosition();
                Position futurePosition = readFuturePosition();
                board.pieceMove(presentPosition, futurePosition);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
                continue;
            }
        }
    }

    private Board setJanggiBoard() {
        PieceInitializer pieceInitializer = new PieceInitializer();
        return new Board(pieceInitializer.generate());
    }

    private Position readPresentPosition() {
        while (true) {
            try {
                return inputView.readPresentPosition();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Position readFuturePosition() {
        while (true) {
            try {
                return inputView.readFuturePosition();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
