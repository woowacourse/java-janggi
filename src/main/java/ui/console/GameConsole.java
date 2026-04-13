package ui.console;

import static domain.player.Team.CHO;
import static domain.player.Team.HAN;

import common.JanggiException;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.game.Game;
import domain.player.Name;
import domain.player.Player;
import domain.player.Players;
import domain.player.Team;
import domain.position.Position;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import repository.GameRepository;
import repository.H2ConnectionManager;
import repository.H2GameRepository;
import ui.view.InputView;
import ui.view.OutputView;

public class GameConsole {
    InputView inputView = new InputView();
    OutputView outputView = new OutputView();
    GameRepository gameRepository = new H2GameRepository(new H2ConnectionManager());

    private long gameId;
    private Game game;

    public void run() {
        game = createGame();
        outputView.printBoard(game.getBoardMap());
        outputView.printCaughtPieces(game.getCaughtPieces());
        while (game.isRunning()) {
            playTurn();
        }
        if (!game.isRunning()) {
            outputView.printWinner(game.getWinner());
        }
    }

    private Game createGame() {
        return retryOnInvalidInput(() -> {
            if (gameRepository.count() == 0) {
                return createNewGame();
            }
            String newOrLoadOption = inputView.askNewOrLoadOption();
            if ("y".equals(newOrLoadOption)) {
                return loadGame();
            }
            if ("n".equals(newOrLoadOption)) {
                return createNewGame();
            }
            throw new JanggiException("y 혹은 n을 입력해주세요.");
        });
    }

    private Game createNewGame() {
        Players players = createPlayers();
        Board board = createBoard();
        Game newGame = new Game(players, board);
        gameId = gameRepository.create(newGame);
        return newGame;
    }

    private Game loadGame() {
        outputView.printGameList(gameRepository.findAll());
        return retryOnInvalidInput(() -> {
            gameId = inputView.askGameId();
            return gameRepository.findBy(gameId);
        });
    }

    private void playTurn() {
        outputView.printPlayerTurnMessage(game.getCurrentPlayerName(), game.getCurrentTeam());

        Position source = retryOnInvalidInput(() -> {
            Position position = createSource();
            Set<Position> movablePositions = game.select(position);
            outputView.printBoard(game.getBoardMap(), movablePositions);
            return position;
        });

        retryOnInvalidInput(() -> {
            Position destination = createDestination();
            game.move(source, destination);
        });
        outputView.printBoard(game.getBoardMap());
        outputView.printCaughtPieces(game.getCaughtPieces());
        outputView.printScore(game.getScore().getChoScore(), game.getScore().getHanScore());

        gameRepository.update(game, gameId);
    }

    private <T> T retryOnInvalidInput(Supplier<T> function) {
        while (true) {
            try {
                return function.get();
            } catch (JanggiException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void retryOnInvalidInput(Runnable action) {
        while (true) {
            try {
                action.run();
                return;
            } catch (JanggiException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Position createSource() {
        return retryOnInvalidInput(() -> {
            List<Integer> numbers = inputView.askSourcePosition();
            return new Position(numbers.getFirst(), numbers.getLast());
        });
    }

    private Position createDestination() {
        return retryOnInvalidInput(() -> {
            List<Integer> numbers = inputView.askDestinationPosition();
            return new Position(numbers.getFirst(), numbers.getLast());
        });
    }

    private Player createChoPlayer() {
        return retryOnInvalidInput(() -> {
            String choName = inputView.askChoPlayerName();
            return createPlayer(choName, CHO);
        });
    }

    private Player createHanPlayer() {
        return retryOnInvalidInput(() -> {
            String hanName = inputView.askHanPlayerName();
            return createPlayer(hanName, HAN);
        });
    }

    private Players createPlayers() {
        Player choPlayer = createChoPlayer();
        return retryOnInvalidInput(() -> {
            Player hanPlayer = createHanPlayer();
            return new Players(List.of(choPlayer, hanPlayer));
        });
    }

    private Player createPlayer(String name, Team team) {
        return new Player(new Name(name), team);
    }

    private Board createBoard() {
        Formation choFormation = createChoFormation();
        Formation hanFormation = createHanFormation();
        return BoardFactory.createWithFormation(choFormation, hanFormation);
    }

    private Formation createChoFormation() {
        return retryOnInvalidInput(() -> {
            int choPositionInput = inputView.askChoPositionInput();
            return Formation.from(choPositionInput);
        });
    }

    private Formation createHanFormation() {
        return retryOnInvalidInput(() -> {
            int hanPositionInput = inputView.askHanPositionInput();
            return Formation.from(hanPositionInput);
        });
    }
}
