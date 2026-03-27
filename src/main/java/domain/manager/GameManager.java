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
        outputView.printPlayerTurnMessage(turnManager.currentTurn().getName(), turnManager.currentTurnTeam().name());

        retryOnInvalidInput(() -> {
            Position src = createSourcePosition();
            Position dest = createDestPosition();
            Piece piece = board.move(src, dest);
            if (piece.isNotNone()) {
                turnManager.currentTurn().addCatchedPiece(piece);
            }
            return null;
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

    private Position createSourcePosition() {
        return retryOnInvalidInput(() -> {
            List<Integer> numbers = inputView.askSourcePosition();
            Position src = new Position(numbers.getFirst(), numbers.getLast());
            if (!board.isPieceSameTeam(src, turnManager.currentTurnTeam())) {
                throw new IllegalArgumentException("다른 팀입니다.");
            }
            return src;
        });
    }

    private Position createDestPosition() {
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
