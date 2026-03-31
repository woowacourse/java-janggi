package domain.console;

import static domain.player.Team.CHO;
import static domain.player.Team.HAN;

import common.exception.JanggiException;
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
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class GameConsole {
    InputView inputView = new InputView();
    OutputView outputView = new OutputView();

    private Game game;

    public void run() {
        game = createGame();
        outputView.printBoard(game.getBoardMap());
        while (game.isRunning()) {
            playTurn();
        }
        if (!game.isRunning()) {
            outputView.printWinner(game.getWinner());
        }
    }

    private Game createGame() {
        Players players = createPlayers();
        Board board = createBoard();
        return new Game(players, board);
    }

    private void playTurn() {
        outputView.printPlayerTurnMessage(game.getCurrentPlayerName(), game.getCurrentTeam());
        retryOnInvalidInput(this::executeMove);
        outputView.printBoard(game.getBoardMap());
    }

    private void executeMove() {
        Position source = createSource();
        Position destination = createDestination();
        game.move(source, destination);
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
