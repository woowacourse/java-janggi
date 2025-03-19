package controller;

import domain.board.*;
import domain.piece.Piece;
import view.InputView;
import view.OutputView;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.createBoard();
        outputView.printBorad(board);
    }
}
