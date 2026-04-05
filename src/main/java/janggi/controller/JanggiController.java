package janggi.controller;

import janggi.domain.board.BoardInitializer;
import janggi.domain.board.Position;
import janggi.domain.game.JanggiGame;
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
        JanggiGame game = new JanggiGame(BoardInitializer.initializeBoard(
                openingFormationChoices.getFirst(),
                openingFormationChoices.getLast()));

        while (!game.isOver()) {
            try {
                outputView.printBoardMap(BoardDto.from(game.getBoard()));
                outputView.printCurrentTurn(TurnDto.from(game.getTurn()));

                Position startPiecePosition = retryOnException(this::getStartPiecePosition);
                Position endPiecePosition = retryOnException(this::getEndPiecePosition);
                game.move(startPiecePosition, endPiecePosition);
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
