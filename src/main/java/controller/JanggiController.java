package controller;

import domain.JanggiGame;
import domain.board.Point;
import domain.player.Player;
import domain.player.Team;
import exceptions.JanggiGameRuleWarningException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import repository.DAOService;
import view.InputView;
import view.OutputView;
import vo.Choice;

public final class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;
    private final DAOService daoService;

    public JanggiController(final InputView inputView,
                            final OutputView outputView,
                            final DAOService daoService
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.daoService = daoService;
    }

    public void run() {
        // 룸 생성하기 OR 룸 불러오기
        initGame();
    }

    private void initGame() {
        try {
            final JanggiGame game = initialJanggiGame();
            outputView.printBoard(game.getBoard());
            playJanggi(game);
        } catch (RuntimeException e) {
            outputView.printError(e.getMessage());
        }
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
        final int gameId = daoService.createGameRoom();

        final Map<Player, Choice> elephantLocatorByTeam = new LinkedHashMap<>();
        for (final Team team : Team.values()) {
            final Choice choice = inputView.readChoiceForElephantLocation(team.toString());
            final Player player = daoService.createPlayer(team, gameId);
            elephantLocatorByTeam.put(player, choice);
        }
        final List<Player> players = new ArrayList<>(elephantLocatorByTeam.keySet());
        return JanggiGame.setup(gameId, elephantLocatorByTeam, players);
    }

    private boolean canProcessMove(final Point start, final Point arrival, final JanggiGame game) {
        try {
            return game.canMove(start, arrival);
        } catch (JanggiGameRuleWarningException e) {
            outputView.printWarring(e.getMessage());
            return true;
        }
    }

    private <T> T handleInput(Supplier<T> inputSupplier) {
        try {
            return inputSupplier.get();
        } catch (JanggiGameRuleWarningException e) {
            outputView.printWarring(e.getMessage());
            return handleInput(inputSupplier);
        }
    }
}
