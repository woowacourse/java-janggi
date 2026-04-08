package application;

import domain.Game;
import domain.Position;
import domain.Side;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.player.Name;
import domain.player.Players;
import domain.score.RemainingPieceScorePolicy;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import persistence.JdbcGameRepository;
import persistence.MoveCommand;
import persistence.SavedGame;
import persistence.SavedGameSummary;
import view.InputView;
import view.OutputView;
import view.parser.GameStartCommand;
import view.parser.InputParser;

public class GameManager {
    private final InputView inputView;
    private final OutputView outputView;
    private final JdbcGameRepository gameRepository;

    public GameManager(InputView inputView, OutputView outputView, JdbcGameRepository gameRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameRepository = gameRepository;
    }

    public void play() {
        GameSession session = loadOrInitializeGame();
        Game game = session.game();
        outputView.printBoard(game.getBoard());
        printScore(game);
        while (!game.isOver()) {
            playTurn(session);
        }
        gameRepository.finishGame(session.gameId());
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
        List<SavedGameSummary> savedGames = gameRepository.findInProgressGames();
        if (savedGames.isEmpty()) {
            throw new IllegalArgumentException("불러올 진행 중 게임이 없습니다. 새로 시작을 선택하세요.");
        }
        outputView.printSavedGames(savedGames);
        int selectedNumber = retry(
                () -> InputParser.parseMenuNumber(inputView.readSavedGameNumber(), 1, savedGames.size())
        );
        long selectedGameId = savedGames.get(selectedNumber - 1).id();
        Optional<SavedGame> savedGame = gameRepository.findInProgressById(selectedGameId);
        if (savedGame.isEmpty()) {
            throw new IllegalArgumentException("선택한 게임을 불러올 수 없습니다. 다시 시도하세요.");
        }
        return restoreGame(savedGame.get());
    }

    private GameSession restoreGame(SavedGame savedGame) {
        Players players = Players.createInitial(
                new Name(savedGame.choPlayerName()),
                new Name(savedGame.hanPlayerName())
        );
        Board board = BoardFactory.create(savedGame.choFormation(), savedGame.hanFormation());
        Game game = new Game(board, players, new RemainingPieceScorePolicy());
        for (MoveCommand move : savedGame.moves()) {
            game.move(move.source(), move.target());
        }
        outputView.printResume(savedGame.choPlayerName(), savedGame.hanPlayerName(), savedGame.moveCount());
        return new GameSession(savedGame.id(), game, savedGame.moveCount());
    }

    private GameSession initializeNewGame() {
        InitializedPlayers initializedPlayers = initializePlayers();
        Formation choFormation = getFormation(Side.CHO);
        Formation hanFormation = getFormation(Side.HAN);
        Board board = BoardFactory.create(choFormation, hanFormation);
        Game game = new Game(board, initializedPlayers.players(), new RemainingPieceScorePolicy());
        long gameId = gameRepository.createGame(
                initializedPlayers.choName().name(),
                initializedPlayers.hanName().name(),
                choFormation,
                hanFormation
        );
        return new GameSession(gameId, game, 0);
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
            game.selectSource(source).validateDestinations(target);
            int nextTurn = session.nextMoveCount();
            gameRepository.saveMove(session.gameId(), nextTurn, source, target);
            game.move(source, target);
            session.updateMoveCount(nextTurn);
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
