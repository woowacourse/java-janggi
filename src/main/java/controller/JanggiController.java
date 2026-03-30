package controller;

import domain.JanggiGame;
import domain.Position;
import dto.SelectPositionRequest;
import exception.JanggiGameException;
import java.util.StringTokenizer;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final JanggiGame janggiGame;

    public JanggiController(JanggiGame janggiGame) {
        this.janggiGame = janggiGame;
    }

    public void run() {
        while (!janggiGame.isGameFinished()) {
            playGame();
        }

        System.out.println();
        OutputView.printBoard(janggiGame.allFactors());
        System.out.println(janggiGame.gameStatus());
    }

    private void playGame() {
        OutputView.printBoard(janggiGame.allFactors());
        OutputView.printCurrentPlayerTurn(janggiGame.currentPlayerTurn());
        execute(this::playerPhase);
    }

    private void playerPhase() {
        SelectPositionRequest selectRequest = InputView.selectPiecePosition();
        Position selected = Position.of(selectRequest.row(), selectRequest.col());

        SelectPositionRequest targetRequest = InputView.selectTargetPositionOf(janggiGame.findPieceInfoAt(selected));
        Position target = Position.of(targetRequest.row(), targetRequest.col());

        janggiGame.move(selected, target);
    }

    private void execute(ExecutableTask task) {
        while (true) {
            try {
                task.execute();
                return;
            } catch (JanggiGameException e) {
                System.out.println(e.getMessage());
                System.out.println();
            }
        }
    }
}
