package application;

import domain.Game;
import domain.Position;
import domain.Side;
import domain.board.Formation;
import domain.player.Name;
import domain.player.Players;
import java.util.List;
import java.util.function.Supplier;
import persistence.SavedGameSummary;
import view.InputView;
import view.OutputView;
import view.parser.GameStartCommand;
import view.parser.InputParser;

public class GameManager {
    private final InputView inputView;
    private final OutputView outputView;
    private final GameSessionHandler gameSessionHandler;

    public GameManager(InputView inputView, OutputView outputView, GameSessionHandler gameSessionHandler) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameSessionHandler = gameSessionHandler;
    }

    public void play() {
        GameSession session = loadOrInitializeGame();
        Game game = session.game();
        outputView.printBoard(game.getBoard());
        printScore(game);
        while (!game.isOver()) {
            playTurn(session);
        }
        gameSessionHandler.finish(session);
        printFinalScore(game);
        outputView.printWinner(game.getWinner());
    }

    private GameSession loadOrInitializeGame() {
        return retry(() -> {
            GameStartCommand command = InputParser.parseGameStartCommand(inputView.readGameStartCommand());
            if (command.isNewGame()) {
                return initializeNewGame();
            }
            return selectAndRestoreGame();
        });
    }

    private GameSession selectAndRestoreGame() {
        List<SavedGameSummary> savedGames = gameSessionHandler.findInProgressGames();
        if (savedGames.isEmpty()) {
            throw new IllegalArgumentException("불러올 진행 중 게임이 없습니다. 새로 시작을 선택하세요.");
        }
        outputView.printSavedGames(savedGames);
        int selectedNumber = retry(
                () -> InputParser.parseMenuNumber(inputView.readSavedGameNumber(), 1, savedGames.size())
        );
        SavedGameSummary selectedGame = savedGames.get(selectedNumber - 1);
        GameSession restoredSession = gameSessionHandler.restoreSession(selectedGame.id());
        outputView.printResume(selectedGame.choPlayerName(), selectedGame.hanPlayerName(), selectedGame.moveCount());
        return restoredSession;
    }

    private GameSession initializeNewGame() {
        InitializedPlayers initializedPlayers = initializePlayers();
        Formation choFormation = getFormation(Side.CHO);
        Formation hanFormation = getFormation(Side.HAN);
        return gameSessionHandler.initializeNewSession(
                initializedPlayers.choName(),
                initializedPlayers.hanName(),
                choFormation,
                hanFormation
        );
    }

    private InitializedPlayers initializePlayers() {
        Name choName = getPlayerName(Side.CHO);
        return retry(() -> {
            Name hanName = getPlayerName(Side.HAN);
            Players players = Players.createInitial(choName, hanName);
            return new InitializedPlayers(choName, hanName, players);
        });
    }

    private Name getPlayerName(Side side) {
        return retry(() -> InputParser.parseName(inputView.readPlayerName(side)));
    }

    private Formation getFormation(Side side) {
        return retry(() -> InputParser.parseFormation(inputView.readFormation(side)));
    }

    private void playTurn(GameSession session) {
        Game game = session.game();
        Position source = selectPiecePosition(game);
        retry(() -> {
            Position target = InputParser.parsePosition(inputView.readTargetPosition());
            gameSessionHandler.move(session, source, target);
        });
        outputView.printBoard(game.getBoard());
        printScore(game);
    }

    private void printScore(Game game) {
        outputView.printScore(game.getScore(Side.CHO), game.getScore(Side.HAN));
    }

    private void printFinalScore(Game game) {
        outputView.printFinalScore(game.getScore(Side.CHO), game.getScore(Side.HAN));
    }

    private Position selectPiecePosition(Game game) {
        return retry(() -> {
            Position position = InputParser.parsePosition(inputView.readSourcePosition(game.getCurrentSide()));
            List<Position> destinations = game.selectSource(position).getPositions();
            outputView.printDestinations(destinations);
            return position;
        });
    }

    private <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void retry(Runnable action) {
        while (true) {
            try {
                action.run();
                return;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
