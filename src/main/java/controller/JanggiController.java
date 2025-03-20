package controller;

import domain.Board;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(
        final InputView inputView,
        final OutputView outputView
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        final Board board = Board.initialize();
        outputView.printBoard(board.getPieces());
    }
}
