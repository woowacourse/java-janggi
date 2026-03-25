package janggi;

import janggi.domain.Board;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiRunner {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiRunner(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void execute() {
        Board board = Board.createInitialBoard();
        outputView.printStartMessage();
        outputView.printBoard(board.makeSpots());
    }
}
