package janggi;

import janggi.board.Board;
import janggi.board.PieceInitializer;
import janggi.position.BoardPosition;
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
            outputView.printJanggiBoard(board.getJanggiPan());

            try {
                BoardPosition presentPosition = readPresentPosition();
                BoardPosition futurePosition = readFuturePosition();
                board.updateBoard(presentPosition, futurePosition);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
                continue;
            }
        }
    }

    private Board setJanggiBoard() {
        PieceInitializer pieceInitializer = new PieceInitializer();
        return new Board(pieceInitializer.hanInit(), pieceInitializer.choInit());
    }

    private BoardPosition readPresentPosition() {
        while (true) {
            try {
                return inputView.readPresentPosition();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private BoardPosition readFuturePosition() {
        while (true) {
            try {
                return inputView.readFuturePosition();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
