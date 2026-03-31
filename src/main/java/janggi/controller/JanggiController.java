package janggi.controller;

import janggi.domain.board.Board;
import janggi.domain.board.BoardInitializer;
import janggi.domain.board.Position;
import janggi.dto.BoardDto;
import janggi.dto.TurnDto;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;
import java.util.function.Supplier;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        List<Integer> openingFormationChoices = retryOnException(inputView::readOpeningFormationChoice);
        Board board = BoardInitializer.initializeBoard(openingFormationChoices.getFirst(),
                openingFormationChoices.getLast());

        while (true) {
            try {
                outputView.printBoardMap(BoardDto.from(board));
                outputView.printCurrentTurn(TurnDto.from(board.getTurn()));

                Position startPiecePosition = retryOnException(this::getStartPiecePosition);
                Position endPiecePosition = retryOnException(this::getEndPiecePosition);
                board.move(startPiecePosition, endPiecePosition);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Position getEndPiecePosition() {
        List<Integer> endPosition = inputView.readEndPiecePosition();
        return new Position(endPosition.getFirst(), endPosition.getLast());
    }

    private Position getStartPiecePosition() {
        List<Integer> startPosition = inputView.readStartPiecePosition();
        return new Position(startPosition.getFirst(), startPosition.getLast());
    }

    private <T> T retryOnException(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
