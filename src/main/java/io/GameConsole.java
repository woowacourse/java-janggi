package io;

import domain.command.Command;
import domain.game.JanggiGame;
import domain.state.GameState;
import domain.vo.Arrangement;
import domain.vo.Arrangements;
import domain.vo.Coordinate;
import domain.vo.Team;
import java.util.function.Supplier;

public class GameConsole {
    private final OutputView outputView;
    private final InputView inputView;
    private final JanggiGame janggiGame;

    public GameConsole() {
        this.outputView = new OutputView();
        this.inputView = new InputView();
        this.janggiGame = new JanggiGame();
    }

    public void run() {
        ///
//        Arrangements arrangements = new Arrangements(
//                readArrangement(Team.HAN),
//                readArrangement(Team.CHO)
//        );
//
//        janggiGame.setupBoard(arrangements);
        ///
        while (true) {
            outputView.printBoard(janggiGame.getBoard(), janggiGame.getTurn());
            movePiece();
            janggiGame.nextTurn();

            break;
        }
        ///

        while (true) {
            janggiGame.displayRequestCommand(outputView);
            janggiGame.processCommand(inputView.readCommand());

            break;
        }
    }

    private void movePiece() {
        retryUntilSuccess(() -> {
            outputView.printPieceMovement(janggiGame.getTurn());
            janggiGame.move(Coordinate.toCoordinate(inputView.readMoveCommand(janggiGame.getTurn())));
            return null;
        });
    }

    private Arrangement readArrangement(Team team) {
        return retryUntilSuccess(() -> {
            outputView.printSetupTable(team);
            return Arrangement.toArrangement(inputView.readSetupCommand());
        });
    }

    private <T> T retryUntilSuccess(Supplier<T> action) {
        try {
            return action.get();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return retryUntilSuccess(action);
        }
    }
}
