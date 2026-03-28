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
//            playGame();
        }

        String s = janggiGame.gameStatus();
        System.out.println(s);
    }

//    private void playGame() {
//        janggiGame.
//        OutputView.printBoard(janggiGame.allFactors());
//        OutputView.printCurrentPlayerTurn(janggiGame.currnetPlayerTurn());
//        execute(this::playerPhase);
//    }
//
//    private void playerPhase() {
//        String input = InputView.selectPiecePosition();
//        String[] split = input.split(", ");
//        Position selected = Position.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
//
//        input = InputView.selectTargerPosition();
//        split = input.split(", ");
//        Position target = Position.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
//
//        janggiGame.move(selected, target);
//    }
//
//
//    private void execute(ExecutableTask task) {
//        while (true) {
//            try {
//                task.execute();
//                return;
//            } catch (IllegalArgumentException e) {
//                e.getMessage();
//            }
//        }
//    }
}
