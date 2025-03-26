package controller;

import domain.Janggi;
import domain.Team;
import domain.board.BoardPosition;
import java.util.Arrays;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import manager.JanggiManager;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final JanggiManager janggiManager;

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(
            final JanggiManager janggiManager,
            final InputView inputView,
            final OutputView outputView
    ) {
        this.janggiManager = janggiManager;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        final Janggi janggi = janggiManager.loadOrCreateJanggi();

        while (!janggiManager.isGameFinish(janggi)) {
            printJanggiProcess(janggi);

            try {
                final BoardPosition selectBoardPosition = createSelectBoardPosition();
                final BoardPosition destinationBoardPosition = createDestinationBoardPosition();

                janggiManager.processTurn(
                        janggi,
                        selectBoardPosition,
                        destinationBoardPosition
                );
            } catch (IllegalArgumentException e) {
                outputView.printInputExceptionMessage(e);
            }
        }

        outputView.printWinnerTeam(janggi.findWinnerTeam());
    }

    private void printJanggiProcess(final Janggi janggi) {
        outputView.printScore(
                janggiManager.findScore(janggi, Team.RED),
                janggiManager.findScore(janggi, Team.GREEN)
        );
        outputView.printCurrentTurn(janggiManager.getCurrentTeam(janggi));
        outputView.printBoard(janggiManager.getPieces(janggi));
    }

    private BoardPosition createSelectBoardPosition() {
        final String selectPosition = inputView.inputSelectPosition();
        return createBoardPosition(selectPosition);
    }

    private BoardPosition createDestinationBoardPosition() {
        final String destinationPosition = inputView.inputDestinationPosition();
        return createBoardPosition(destinationPosition);
    }

    private BoardPosition createBoardPosition(final String inputPosition) {
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
        } catch (PatternSyntaxException | NumberFormatException e) {
            throw new IllegalArgumentException("좌표 입력 형식이 잘못되었습니다.");
        }
    }

    private void validateSize(final List<Integer> positions) {
        if (positions.size() != 2) {
            throw new IllegalArgumentException("좌표 입력 형식이 잘못되었습니다.");
        }
    }
}
