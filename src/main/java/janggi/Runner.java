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
    private static final String UNEXPECTED_SERVER_ERROR_LOG_MESSAGE = "예측하지 못한 시스템 오류 발생";
    private static final String SYSTEM_ERROR_MESSAGE = "시스템 오류가 발생하여 게임을 종료합니다.";

    private static final Logger logger = Logger.getLogger(Runner.class.getName());

    public void run() {
        Game game = initArrangeGame();
        playGame(game);
    }

    private Game initArrangeGame() {
        String hanArrangementInput = InputView.askHanArrangement();
        Arrangement hanArrangement = Arrangement.from(hanArrangementInput);

        String choArrangementInput = InputView.askChoArrangement();
        Arrangement choArrangement = Arrangement.from(choArrangementInput);

        return new Game(choArrangement, hanArrangement);
    }

    private void playGame(Game game) {
        while (playTurnGame(game)) {
        }
    }

    private boolean playTurnGame(Game game) {
        try {
            printCurrentStatus(game);
            movePiece(game);
            printCurrentScore(game);
            return isFinishedGame(game);
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e.getMessage());
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, UNEXPECTED_SERVER_ERROR_LOG_MESSAGE, e);
            OutputView.printErrorMessage(SYSTEM_ERROR_MESSAGE);
            return false;
        }
    }

    private void printCurrentStatus(Game game) {
        OutputView.printLine();
        OutputView.printBoard(game.getCurrentBoardDto());
        OutputView.printTurn(game.getCurrentSide());
    }

    private void movePiece(Game game) {
        List<Integer> startPositionInput = InputView.askStartPosition();
        Position startPosition = Position.from(startPositionInput);

        List<Integer> endPositionInput = InputView.askEndPosition();
        Position endPosition = Position.from(endPositionInput);

        game.move(startPosition, endPosition);
    }

    private void printCurrentScore(Game game) {
        OutputView.printLine();
        OutputView.printScore(game.getCurrentSideScore());
    }

    private boolean isFinishedGame(Game game) {
        if (game.isFinished()) {
            OutputView.printWinner(game.getWinnerSide());
            return false;
        }
        return true;
    }
}
