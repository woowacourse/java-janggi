package janggi.controller;

import java.util.List;

import janggi.domain.Board;
import janggi.domain.BoardInitializer;
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
