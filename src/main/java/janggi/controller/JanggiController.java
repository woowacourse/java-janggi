package janggi.controller;

import java.util.List;

import janggi.domain.board.Board;
import janggi.domain.board.BoardInitializer;
import janggi.dto.BoardDto;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        List<Integer> openingFormationChoices = inputView.readOpeningFormationChoice();
        Board board = BoardInitializer.initializeBoard(openingFormationChoices.getFirst(), openingFormationChoices.getLast());
        outputView.printBoardMap(BoardDto.from(board));
    }
}
