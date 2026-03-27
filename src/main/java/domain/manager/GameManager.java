package domain.manager;

import static common.exception.ErrorMessage.DIFFERENT_TEAM;
import static common.exception.ErrorMessage.EMPTY_SOURCE_POSITION;
import static domain.player.Team.CHO;
import static domain.player.Team.HAN;

import common.exception.JanggiException;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.piece.Piece;
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
    private Board board;
    private TurnManager turnManager;

    public void run() {
        board = initialize();
        outputView.printBoard(board);

        while (turnManager.isGameRunning()) {
            playTurn();
        }
    }

    private void playTurn() {
        outputView.printPlayerTurnMessage(turnManager.getCurrentPlayer().getName(),
                turnManager.getCurrentTeam().name());

        retryOnInvalidInput(this::executeMove);

        outputView.printBoard(board);
        turnManager.switchTurn();
    }

    private void executeMove() {
        Position source = createSource();
        Position destination = createDestination();

        Piece caughtPiece = board.move(source, destination);
        if (caughtPiece.isNotNone()) {
            turnManager.getCurrentPlayer().addCaughtPiece(caughtPiece);
        }
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
            Position source = new Position(numbers.getFirst(), numbers.getLast());
            if (board.isPieceNone(source)) {
                throw new JanggiException(EMPTY_SOURCE_POSITION.getMessage());
            }
            if (board.isPieceDifferentTeam(source, turnManager.getCurrentTeam())) {
                throw new JanggiException(DIFFERENT_TEAM.formatted(turnManager.getCurrentTeam()));
            }
            return source;
        });
    }

    private Position createDestination() {
        return retryOnInvalidInput(() -> {
            List<Integer> numbers = inputView.askDestinationPosition();
            return new Position(numbers.getFirst(), numbers.getLast());
        });
    }

    private Board initialize() {
        Player choPlayer = createChoPlayer();
        Player hanPlayer = createHanPlayer();

        turnManager = new TurnManager(choPlayer, hanPlayer);

        Formation choFormation = createChoFormation();
        Formation hanFormation = createHanFormation();

        return BoardFactory.createWithFormation(choFormation, hanFormation);
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
            int hanPositionInput = inputView.askChoPositionInput();
            return createFormation(hanPositionInput);
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
