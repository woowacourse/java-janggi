package domain.manager;

import static domain.player.Team.CHO;
import static domain.player.Team.HAN;

import common.exception.JanggiException;
import domain.board.Formation;
import domain.game.Game;
import domain.game.GameInitializer;
import domain.player.Name;
import domain.player.Player;
import domain.player.Team;
import domain.position.Position;
import java.util.List;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class GameManager {
    InputView inputView = new InputView();
    OutputView outputView = new OutputView();

    private Game game;

    public void run() {
        game = createGame();
        outputView.printBoard(game.getBoardMap());

        while (game.isRunning()) {
            playTurn();
        }
    }

    private Game createGame() {
        String choPlayerName = inputView.askChoPlayerName();
        String hanPlayerName = inputView.askHanPlayerName();
        int choPositionInput = inputView.askChoPositionInput();
        int hanPositionInput = inputView.askHanPositionInput();
        return GameInitializer.initialize(choPlayerName, hanPlayerName, choPositionInput, hanPositionInput);
    }

    private void playTurn() {
        outputView.printPlayerTurnMessage(game.getCurrentPlayerName(), game.getCurrentTeam().name());
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
        String choName = inputView.askChoPlayerName();
        return createPlayer(choName, CHO);
    }

    private Player createHanPlayer() {
        String hanName = inputView.askHanPlayerName();
        return createPlayer(hanName, HAN);
    }

    private Player createPlayer(String name, Team team) {
        return new Player(new Name(name), team);
    }

    private Formation createChoFormation() {
        return retryOnInvalidInput(() -> {
            int choPositionInput = inputView.askChoPositionInput();
            return createFormation(choPositionInput);
        });
    }

    private Formation createHanFormation() {
        return retryOnInvalidInput(() -> {
            int hanPositionInput = inputView.askHanPositionInput();
            return createFormation(hanPositionInput);
        });
    }

    private Formation createFormation(int positionInput) {
        return Formation.from(positionInput);
    }
}
