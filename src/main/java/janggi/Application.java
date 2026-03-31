package janggi;

import janggi.domain.board.Board;
import janggi.domain.board.BoardInitializer;
import janggi.domain.board.Position;
import janggi.dto.BoardDto;
import janggi.dto.OpeningFormationChoices;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;

public class Application {
    private final InputView inputView;
    private final OutputView outputView;

    public Application() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public static void main(String[] args) {
        new Application().run();
    }

    private void run() {
        OpeningFormationChoices openingFormationChoices = readOpeningFormationChoiceUntilValid();
        Board board = BoardInitializer.initializeBoard(openingFormationChoices.hanChoice(),
                openingFormationChoices.choChoice());

        while (isPlaying()) {
            outputView.printBoardMap(BoardDto.from(board));
            Position startPiecePosition = readStartPositionUntilValid();
            Position endPiecePosition = readEndPositionUntilValid();
            tryMove(board, startPiecePosition, endPiecePosition);
        }
    }

    private boolean isPlaying() {
        return true;
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
        return toPosition(endPosition);
    }

    private Position getStartPiecePosition() {
        List<Integer> startPosition = inputView.readStartPiecePosition();
        return toPosition(startPosition);
    }

    private Position toPosition(List<Integer> values) {
        int x = values.get(0);
        int y = values.get(1);
        return new Position(x, y);
    }
}
