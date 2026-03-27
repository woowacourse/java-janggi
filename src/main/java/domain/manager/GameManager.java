package domain.manager;

import static domain.player.Team.CHO;
import static domain.player.Team.HAN;

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
        outputView.printBoard(board.createDTO().board());

        while (turnManager.isGameRunning()) {
            playTurn();
        }
    }

    private void playTurn() {
        outputView.printPlayerTurnMessage(turnManager.getCurrentPlayer().getName(),
                turnManager.getCurrentTeam().name());

        retryOnInvalidInput(() -> {
            Position source = createSource();
            Position destination = createDestination();
            Piece piece = board.move(source, destination);
            if (piece.isNotNone()) {
                turnManager.getCurrentPlayer().addCaughtPiece(piece);
            }
        });

        outputView.printBoard(board.createDTO().board());
        turnManager.switchTurn();
    }

    private <T> T retryOnInvalidInput(Supplier<T> function) {
        while (true) {
            try {
                return function.get();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void retryOnInvalidInput(Runnable action) {
        while (true) {
            try {
                action.run();
                return; // 에러 없이 실행되었다면 무한 루프 탈출
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Position createSource() {
        return retryOnInvalidInput(() -> {
            List<Integer> numbers = inputView.askSourcePosition();
            Position source = new Position(numbers.getFirst(), numbers.getLast());
            if (!board.isPieceSameTeam(source, turnManager.getCurrentTeam())) {
                throw new IllegalArgumentException("다른 팀입니다.");
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
        int choPositionInput = retryOnInvalidInput(inputView::askChoPositionInput);
        return createFormation(choPositionInput);
    }

    private Formation createHanFormation() {
        int hanPositionInput = retryOnInvalidInput(inputView::askHanPositionInput);
        return createFormation(hanPositionInput);
    }


    private Formation createFormation(int positionInput) {
        return Formation.from(positionInput);
    }
}
