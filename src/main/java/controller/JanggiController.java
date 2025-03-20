package controller;

import domain.BoardPosition;
import domain.Janggi;
import java.util.Arrays;
import java.util.List;
import view.InputView;
import view.OutputView;

public class JanggiController {

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
        final List<Integer> positions = parseBoardPosition(inputPosition);
        validateSize(positions);
        final int x = positions.getFirst();
        final int y = positions.getLast();
        return new BoardPosition(x, y);
    }

    private List<Integer> parseBoardPosition(final String inputPosition) {
        try {
            return Arrays.stream(inputPosition.split(",", -1))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .toList();
        } catch (Exception e) {
            throw new IllegalArgumentException("좌표 입력 형식이 잘못되었습니다.");
        }
    }

    private void validateSize(final List<Integer> positions) {
        if (positions.size() != 2) {
            throw new IllegalArgumentException("좌표 입력 형식이 잘못되었습니다.");
        }
    }
}
