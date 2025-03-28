package controller;

import domain.JanggiGame;
import domain.board.Point;
import domain.player.Team;
import exceptions.JanggiGameRuleWarningException;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;
import vo.Choice;

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
            final Team currentTeam = game.getTeamOnCurrentTurn();
            final List<List<Choice>> moveRequest = handleInput(() -> inputView.readMovementRequest(currentTeam));
            final Point start = Point.generateStartPoint(moveRequest);
            final Point arrival = Point.generateArrivalPoint(moveRequest);
            if (!canProcessMove(start, arrival, game)) {
                outputView.printWinner(currentTeam);
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
        final EnumMap<Team, Choice> elephantLocatorByTeam = new EnumMap<>(Team.class);
        for (final Team team : Team.values()) {
            final Choice choice = inputView.readChoiceForElephantLocation(team.toString());
            elephantLocatorByTeam.put(team, choice);
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
