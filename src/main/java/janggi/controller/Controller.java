package janggi.controller;

import janggi.domain.Board;
import janggi.domain.BoardFactory;
import janggi.dto.BoardDto;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Controller {
    private final InputView inputView;
    private final OutputView outputView;

    public Controller(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        outputView.printStartMessage();
        Board board = new Board(BoardFactory.generate());
        outputView.printBoard(BoardDto.from(board));
    }
}
