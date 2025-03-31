package controller;

import dao.ConnectionProvider;
import domain.board.BoardPosition;
import domain.janggi.Janggi;
import domain.janggi.Team;
import manager.JanggiManager;
import util.InputParser;
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
            ConnectionProvider.getConnection();
        } catch (IllegalStateException e) {
            outputView.printExceptionMessage(e);
            return;
        }
        selectJanggiGame();
    }

    private void selectJanggiGame() {
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
        } catch (IllegalStateException e) {
            outputView.printInputExceptionMessage(e);
            selectJanggiGame();
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
            } catch (IllegalArgumentException | IllegalStateException e) {
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
        return InputParser.parseBoardPosition(selectPosition);
    }

    private BoardPosition createDestinationBoardPosition() {
        final String destinationPosition = inputView.inputDestinationPosition();
        return InputParser.parseBoardPosition(destinationPosition);
    }
}
