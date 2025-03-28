package controller;

import domain.JanggiGame;
import domain.board.Point;
import domain.player.TeamType;
import exceptions.JanggiGameRuleWarningException;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public final class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        final JanggiGame game = initialJanggiGame();
        outputView.printBoard(game.getBoard());
        playJanggi(game);
    }

    private void playJanggi(final JanggiGame game) {
        while (true) {
            final TeamType currentTeamType = game.getTeamOnCurrentTurn();
            final List<List<Integer>> moveRequest = handleInput(() -> inputView.readMovementRequest(currentTeamType));
            final Point start = Point.generateStartPoint(moveRequest);
            final Point arrival = Point.generateArrivalPoint(moveRequest);
            if (!canProcessMove(start, arrival, game)) {
                outputView.printWinner(currentTeamType);
                break;
            }
            game.movePieceOnBoard(start, arrival);
            game.switchTurn();
            outputView.printBoard(game.getBoard());
            outputView.printScores(game.wrapPlayersScore());
        }
    }

    private JanggiGame initialJanggiGame() {
        outputView.printTurnGuide();
        return handleInput(this::setupGame);
    }

    private JanggiGame setupGame() {
        final EnumMap<TeamType, Integer> elephantLocatorByTeam = new EnumMap<>(TeamType.class);
        for (final TeamType teamType : TeamType.values()) {
            final int choice = inputView.readChoiceForElephantLocation(teamType.toString());
            elephantLocatorByTeam.put(teamType, choice);
        }
        return JanggiGame.setup(elephantLocatorByTeam);
    }

    private boolean canProcessMove(final Point start, final Point arrival, final JanggiGame game) {
        try {
            return game.canMove(start, arrival);
        } catch (JanggiGameRuleWarningException e) {
            outputView.printError(e.getMessage());
            return true;
        }
    }

    private <T> T handleInput(Supplier<T> inputSupplier) {
        try {
            return inputSupplier.get();
        } catch (JanggiGameRuleWarningException e) {
            outputView.printError(e.getMessage());
            return handleInput(inputSupplier);
        }
    }
}
