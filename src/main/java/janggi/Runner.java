package janggi;

import janggi.domain.Arrangement;
import janggi.domain.Game;
import janggi.domain.Position;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Runner {
    private static final Logger logger = Logger.getLogger(Runner.class.getName());
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
        while (playTurnGame()) {
        }
    }

    private boolean playTurnGame() {
        try {
            printCurrentStatus();
            movePiece();
            return isFinishedGame();
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e.getMessage());
            return true;
        } catch (Exception e) {
            logger.log(Level.ALL, "시스템 오류", e);
            return false;
        }
    }

    private void printCurrentStatus() {
        OutputView.printBoard(game.getCurrentBoardDto());
        OutputView.printTurn(game.getCurrentSide());
    }

    private void movePiece() {
        List<String> startPositionInput = InputView.askStartPosition();
        Position startPosition = Position.from(startPositionInput);

        List<String> endPositionInput = InputView.askEndPosition();
        Position endPosition = Position.from(endPositionInput);

        game.move(startPosition, endPosition);
    }

    private boolean isFinishedGame() {
        if (game.isFinished()) {
            OutputView.printWinner(game.getWinnerSide());
            return false;
        }
        return true;
    }
}
