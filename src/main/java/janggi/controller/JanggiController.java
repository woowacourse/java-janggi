package janggi.controller;

import janggi.model.Board;
import janggi.model.initializer.BoardInitializer;
import janggi.view.OutputView;

public class JanggiController {

    private final OutputView outputView;
    private final BoardInitializer boardInitializer;

    public JanggiController(OutputView outputView, BoardInitializer boardInitializer) {
        this.outputView = outputView;
        this.boardInitializer = boardInitializer;
    }

    public void initializeBoard() {
        Board board = boardInitializer.init();
        outputView.printBoard(board);
    }
}
