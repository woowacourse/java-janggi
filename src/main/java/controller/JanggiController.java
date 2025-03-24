package controller;

import domain.BoardPosition;
import domain.Janggi;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private static final int COORDINATE_PARTS_COUNT = 2;
    private static final String POSITION_DELIMITER = ",";

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

            final String selectPosition = inputView.inputSelectPosition();
            final BoardPosition selectBoardPosition = createBoardPosition(selectPosition);

            final String destinationPosition = inputView.inputDestinationPosition();
            final BoardPosition destinationBoardPosition = createBoardPosition(destinationPosition);

            janggi.processTurn(selectBoardPosition, destinationBoardPosition);
        }
    }

    public BoardPosition createBoardPosition(final String inputPosition) {
        final int[] coordinates = validateAndParse(validateAndSplit(inputPosition));
        return new BoardPosition(coordinates[0], coordinates[1]);
    }

    private String[] validateAndSplit(final String input) {
        final String[] coordinates;
        try {
            coordinates = input.split(POSITION_DELIMITER, -1);
        } catch (Exception e) {
            throw new IllegalArgumentException("좌표는 'x,y' 형식으로 입력해야 합니다.");
        }

        if (coordinates.length != COORDINATE_PARTS_COUNT) {
            throw new IllegalArgumentException("좌표는 'x,y' 형식으로 입력해야 합니다.");
        }

        return coordinates;
    }

    private int[] validateAndParse(final String[] parts) {
        try {
            final int x = Integer.parseInt(parts[0].trim());
            final int y = Integer.parseInt(parts[1].trim());

            return new int[]{x, y};
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("좌표는 숫자로 입력해야 합니다.");
        }
    }
}
