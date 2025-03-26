package controller;

import domain.BoardPosition;
import domain.Janggi;
import java.util.List;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private static final int ROW_INDEX = 0;
    private static final int COLUMN_INDEX = 1;

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(
        final InputView inputView,
        final OutputView outputView
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        final Janggi janggi = Janggi.initialize();
        while (true) {
            outputView.printBoard(janggi.getPieces(), janggi.getCurrentTeam());

            final List<Integer> selectPosition = inputView.inputSelectPosition();
            final BoardPosition selectBoardPosition = createBoardPosition(selectPosition);
            janggi.validateSelectedPiece(selectBoardPosition);

            final List<Integer> destinationPosition = inputView.inputDestinationPosition();
            final BoardPosition destinationBoardPosition = createBoardPosition(destinationPosition);

            janggi.processTurn(selectBoardPosition, destinationBoardPosition);
        }
    }

    public BoardPosition createBoardPosition(final List<Integer> coordinates) {
        validateSize(coordinates);
        return new BoardPosition(coordinates.get(ROW_INDEX), coordinates.get(COLUMN_INDEX));
    }

    private void validateSize(final List<Integer> coordinates) {
        if (coordinates.size() != BoardPosition.COORDINATE_PARTS_COUNT) {
            throw new IllegalArgumentException("좌표의 크기는 2여야 합니다.");
        }
    }
}
