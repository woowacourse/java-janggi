package controller;

import domain.board.BoardPosition;
import domain.janggi.Janggi;
import domain.janggi.Team;
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
        try {
            outputView.printAllJanggiGames(janggiManager.findAllJanggiDtos());
            final String selectJanggiInput = inputView.inputSelectJanggi();

            if (selectJanggiInput.matches("^[0-9]*$")) {
                playJanggi(janggiManager.loadJanggi(Integer.parseInt(selectJanggiInput)));
                return;
            }
            if (selectJanggiInput.equals("new")) {
                playJanggi(janggiManager.createJanggi(inputView.inputJanggiTitle()));
                return;
            }
            throw new IllegalArgumentException("잘못된 입력입니다. 재입력해주세요.");
        } catch (Exception e) {
            outputView.printInputExceptionMessage(e);
            run();
        }
    }

    private void playJanggi(Janggi janggi) {
        while (!janggiManager.isGameFinish(janggi)) {
            printJanggiProcess(janggi);

            try {
                final BoardPosition selectBoardPosition = createSelectBoardPosition();
                final BoardPosition destinationBoardPosition = createDestinationBoardPosition();

                janggi = janggiManager.processTurn(
                        janggi,
                        selectBoardPosition,
                        destinationBoardPosition
                );
            } catch (IllegalArgumentException e) {
                outputView.printInputExceptionMessage(e);
            }
        }

        outputView.printWinnerTeam(janggiManager.findWinnerTeam(janggi));
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
