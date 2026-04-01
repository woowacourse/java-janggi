package janggi;

import janggi.domain.Arrangement;
import janggi.domain.Game;
import janggi.domain.Position;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Runner {
    public void run() {
        Game game = initArrangeGame();
        turnGame(game);
    }

    private Game initArrangeGame() {
        String hanArrangementInput = InputView.askHanArrangement();
        Arrangement hanArrangement = Arrangement.from(hanArrangementInput);

        String choArrangementInput = InputView.askChoArrangement();
        Arrangement choArrangement = Arrangement.from(choArrangementInput);

        return new Game(choArrangement, hanArrangement);
    }

    private void turnGame(Game game) {
        while (!game.isFinished()) {
            playTurnGame(game);
        }
        printWinner(game);
    }

    private void playTurnGame(Game game) {
        try {
            printCurrentStatus(game);
            Position startPosition = Position.from(InputView.askStartPosition());
            Position endPosition = Position.from(InputView.askEndPosition());

            game.move(startPosition, endPosition);
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e.getMessage());
        }
    }

    private void printCurrentStatus(Game game) {
        OutputView.printTurn(game.getCurrentSide());
        OutputView.printBoard(game.getCurrentBoard());
    }

    private void printWinner(Game game) {
        OutputView.printBoard(game.getCurrentBoard());
        OutputView.printWinner(game.getCurrentSide());
    }
}
