package janggi.controller;

import janggi.domain.board.Board;
import janggi.domain.board.BoardInitializer;
import janggi.domain.board.Position;
import janggi.dto.BoardDto;
import janggi.dto.OpeningFormationChoices;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        OpeningFormationChoices openingFormationChoices = readOpeningFormationChoiceUntilValid();
        Board board = BoardInitializer.initializeBoard(openingFormationChoices.hanChoice(),
                openingFormationChoices.choChoice());

        while (true) {
            outputView.printBoardMap(BoardDto.from(board));
            Position startPiecePosition = readStartPositionUntilValid();
            Position endPiecePosition = readEndPositionUntilValid();
            tryMove(board, startPiecePosition, endPiecePosition);
        }
    }

    private void tryMove(Board board, Position from, Position to) {
        try {
            board.move(from, to);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    private OpeningFormationChoices readOpeningFormationChoiceUntilValid() {
        while (true) {
            try {
                return inputView.readOpeningFormationChoice();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Position readStartPositionUntilValid() {
        while (true) {
            try {
                Position startPiecePosition = getStartPiecePosition();
                return startPiecePosition;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Position readEndPositionUntilValid() {
        while (true) {
            try {
                return getEndPiecePosition();
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
}
