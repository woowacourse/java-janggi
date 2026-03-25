package io;

import domain.game.JanggiGame;
import domain.vo.Arrangement;
import domain.vo.Arrangements;
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
        Arrangements arrangements = new Arrangements(
                readArrangement(Team.HAN),
                readArrangement(Team.CHO)
        );

        janggiGame.setupBoard(arrangements);

        outputView.printBoard(janggiGame.getBoard(), janggiGame.getTurn());

        outputView.printPieceMovement(janggiGame.getTurn());
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
