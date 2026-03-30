package controller;

import domain.JanggiGame;
import domain.Position;
import java.util.List;
import utils.InputParser;
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

        OutputView.printGameResult(janggiGame.gameStatus());
    }

    private void playGame() {
        OutputView.printBoard(janggiGame.allFactors());
        OutputView.printCurrentPlayerTurn(janggiGame.gameStatus());
        execute(this::playerPhase);
    }

    private void playerPhase() {
        Position selectedPosition = readSelectedPiecePosition();

        Position targetPosition = getTargetPosition();

        janggiGame.move(selectedPosition, targetPosition);
    }

    private Position readSelectedPiecePosition() {
        String rawSelectPiecePosition = InputView.selectPiecePosition();
        List<Integer> selectPiecePosition = InputParser.parseDelimitedToIntegersStrict(rawSelectPiecePosition);
        Position selectedPosition = new Position(selectPiecePosition.getFirst(), selectPiecePosition.getLast());
        janggiGame.validatePieceSelection(selectedPosition);
        return selectedPosition;
    }

    private static Position getTargetPosition() {
        String rawTargetPosition = InputView.selectTargetPosition();
        List<Integer> target = InputParser.parseDelimitedToIntegersStrict(rawTargetPosition);
        return new Position(target.getFirst(), target.getLast());
    }


    private void execute(ExecutableTask task) {
        while (true) {
            try {
                task.execute();
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
