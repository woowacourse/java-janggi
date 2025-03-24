package controller;

import domain.JanggiGame;
import domain.Team;
import domain.board.Point;
import dto.MovementRequestDto;
import execptions.JanggiGameRuleWarningException;
import java.util.EnumMap;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        outputView.printTurnGuide();
        final JanggiGame game = handleInput(this::setupGame);
        outputView.printBoard(game.getBoard());
        while (true) {
            processMove(game);
        }
    }

    private JanggiGame setupGame() {
        final EnumMap<Team, Integer> elephantLocatorByTeam = new EnumMap<>(Team.class);
        for (final Team team : Team.getActualTeams()) {
            final int choice = inputView.readChoiceForElephantLocation(team.getName());
            elephantLocatorByTeam.put(team, choice);
        }
        return JanggiGame.setup(elephantLocatorByTeam);
    }

    private void processMove(final JanggiGame game) {
        final Team currentPlayerTeam = game.getPlayerTeamOnCurrentTurn();
        try {
            final MovementRequestDto movementRequest = inputView.readMovementRequest(currentPlayerTeam.getName());
            final Point originPoint = movementRequest.getStartPoint();
            final Point arrivalPoint = movementRequest.getArrivalPoint();
            game.move(originPoint, arrivalPoint);
            outputView.printBoard(game.getBoard());
        } catch (JanggiGameRuleWarningException e) {
            outputView.printError(e.getMessage());
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
