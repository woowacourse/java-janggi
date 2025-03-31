package controller;

import dao.DAOService;
import domain.JanggiGame;
import domain.board.Board;
import domain.board.Point;
import domain.player.Player;
import domain.player.Team;
import dto.BoardLocations;
import dto.Choice;
import exceptions.JanggiGameRuleWarningException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

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
        final List<Integer> activeGameIds = daoService.findAllActivateGames();
        if (activeGameIds.isEmpty()) {
            initNewGame();
            return;
        }

        outputView.printActivateGames(activeGameIds);
        final Choice choice = inputView.readChoiceForLoadOrInitialize();
        if (activeGameIds.contains(choice.value())) {
            loadGame(choice);
            return;
        }
        initNewGame();
    }

    private void loadGame(final Choice choice) {
        outputView.printLoadGame(choice);
        final JanggiGame game = loadJanggiGame(choice);
        outputView.printBoard(game.getBoard());
        playJanggi(game);
    }

    private JanggiGame loadJanggiGame(final Choice choice) {
        List<Player> players = daoService.findPlayersByGameId(choice);
        BoardLocations locations = daoService.findLocationByGameId(choice);
        Board board = locations.convertToBoard(players);
        return new JanggiGame(choice.value(), board, players);
    }

    private void initNewGame() {
        final JanggiGame game = initialJanggiGame();
        daoService.registerLocations(new BoardLocations(game.getBoard()));
        outputView.printBoard(game.getBoard());
        playJanggi(game);
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

    private void playJanggi(final JanggiGame game) {
        boolean gameInProgress = true;
        while (gameInProgress) {
            gameInProgress = isGameInProgress(game, gameInProgress);
        }
    }

    private boolean isGameInProgress(final JanggiGame game, boolean gameInProgress) {
        try {
            gameInProgress = processTurn(game);
        } catch (JanggiGameRuleWarningException e) {
            outputView.printWarring(e.getMessage());
        }
        return gameInProgress;
    }

    private boolean processTurn(final JanggiGame game) {
        final Team currentTeam = game.getTeamOnCurrentTurn();
        final List<List<Choice>> moveRequest = handleInput(() -> inputView.readMovementRequest(currentTeam));
        final Point start = Point.generateStartPoint(moveRequest);
        final Point arrival = Point.generateArrivalPoint(moveRequest);

        if (!game.canMovePiece(start, arrival)) {
            gameOver(game, currentTeam);
            return false;
        }

        handleMove(game, start, arrival);
        return true;
    }

    private void handleMove(final JanggiGame game, final Point start, final Point arrival) {
        game.movePieceOnBoard(start, arrival);
        daoService.changeLocation(start, arrival, game.getId());

        game.switchTurn();
        daoService.switchTurn(game.getPlayers());

        outputView.printBoard(game.getBoard());
        outputView.printScores(game.wrapPlayersScore());
    }

    private void gameOver(final JanggiGame game, final Team team) {
        daoService.deactivateGame(game.getId());
        outputView.printWinner(team);
    }

    private <T> T handleInput(final Supplier<T> inputSupplier) {
        try {
            return inputSupplier.get();
        } catch (JanggiGameRuleWarningException e) {
            outputView.printWarring(e.getMessage());
            return handleInput(inputSupplier);
        }
    }
}
