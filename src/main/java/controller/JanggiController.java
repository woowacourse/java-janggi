package controller;

import domain.JanggiGame;
import domain.Position;
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
            janggiGame.checkGameFinished();
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
        String input = InputView.selectPiecePosition();
        String[] split = input.split(",");
        Position selected = Position.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]));

        input = InputView.selectTargetPositionOf(janggiGame.findPieceInfoAt(selected));
        split = input.split(",");
        Position target = Position.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]));

        janggiGame.move(selected, target);
    }


    private void execute(ExecutableTask task) {
        while (true) {
            try {
                task.execute();
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println();
            }
        }
    }
}
