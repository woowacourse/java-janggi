package janggi;

import janggi.domain.Arrangement;
import janggi.domain.Game;
import janggi.domain.Position;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Runner {
    private Game game;

    public void run() {
        initArrangeGame();
        turnGame();
    }

    private void initArrangeGame() {
        String hanArrangementInput = InputView.askHanArrangement();
        Arrangement hanArrangement = Arrangement.from(hanArrangementInput);

        String choArrangementInput = InputView.askChoArrangement();
        Arrangement choArrangement = Arrangement.from(choArrangementInput);

        game = new Game(choArrangement, hanArrangement);
    }

    private void turnGame() {
        while (true) {
            playTurnGame();
        }
    }

    private void playTurnGame() {
        try {
            printCurrentStatus();
            Position startPosition = Position.from(InputView.askStartPosition());
            Position endPosition = Position.from(InputView.askEndPosition());
            game.move(startPosition, endPosition);
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e.getMessage());
        }
    }

    private void printCurrentStatus() {
        OutputView.printBoard(game.getCurrentBoardDto());
        OutputView.printTurn(game.getCurrentSide());
    }
}
