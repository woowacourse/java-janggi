import static domain.player.Team.CHO;
import static domain.player.Team.HAN;

import common.exception.JanggiException;
import domain.board.Formation;
import domain.manager.GameManager;
import domain.player.Name;
import domain.player.Player;
import domain.player.Team;
import domain.position.Position;
import java.util.List;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class Runner {
    private final InputView inputView;
    private final OutputView outputView;
    private GameManager gameManager;

    public Runner(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        initialize();
        outputView.printBoard(gameManager.getBoard());

        while (gameManager.isGameRunning()) {
            playTurn();
        }

        outputView.printResult(gameManager.getCurrentPlayer().getProfile());
    }

    private void playTurn() {
        Player currentPlayer = gameManager.getCurrentPlayer();
        outputView.printPlayerTurnMessage(currentPlayer.getProfile());

        retryOnInvalidInput(() -> {
            Position source = createSource();
            gameManager.validateSource(source);
            Position destination = createDestination();
            gameManager.move(source, destination);
        });

        outputView.printBoard(gameManager.getBoard());
    }

    private Position createSource() {
        List<Integer> numbers = inputView.askSourcePosition();
        return new Position(numbers.getFirst(), numbers.getLast());
    }

    private Position createDestination() {
        List<Integer> numbers = inputView.askDestinationPosition();
        return new Position(numbers.getFirst(), numbers.getLast());
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

    private <T> T retryOnInvalidInput(Supplier<T> action) {
        while (true) {
            try {
                return action.get();
            } catch (JanggiException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void initialize() {
        Player choPlayer = retryOnInvalidInput(this::createChoPlayer);
        Player hanPlayer = retryOnInvalidInput(() -> createHanPlayer(choPlayer));
        Formation choFormation = retryOnInvalidInput(this::createChoFormation);
        Formation hanFormation = retryOnInvalidInput(this::createHanFormation);

        this.gameManager = new GameManager(choPlayer, hanPlayer, choFormation, hanFormation);
    }

    private Player createChoPlayer() {
        String choName = inputView.askChoPlayerName();
        return createPlayer(choName, CHO);
    }

    private Player createHanPlayer(Player choPlayer) {
        String hanName = inputView.askHanPlayerName();
        if (choPlayer.hasName(hanName)) {
            throw new JanggiException("초나라 플레이어와 닉네임이 중복될 수 없습니다.");
        }
        return createPlayer(hanName, HAN);
    }

    private Player createPlayer(String name, Team team) {
        return new Player(new Name(name), team);
    }

    private Formation createChoFormation() {
        int choPositionInput = inputView.askChoPositionInput();
        return Formation.from(choPositionInput);
    }

    private Formation createHanFormation() {
        int hanPositionInput = inputView.askHanPositionInput();
        return Formation.from(hanPositionInput);
    }
}
