package janggi;

import janggi.domain.Arrangement;
import janggi.domain.Game;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.SideScore;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Runner {
    public static final String END_TEXT = "종료";

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
            return executeTurn(game);
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

    private boolean executeTurn(Game game) {
        Optional<List<Integer>> startPositionInput = InputView.askStartPosition();

        if(startPositionInput.isEmpty()) {
            return consentEndGame(game);
        }

        Position startPosition = Position.from(startPositionInput.get());

        List<Integer> endPositionInput = InputView.askEndPosition();
        Position endPosition = Position.from(endPositionInput);
        OutputView.printLine();

        game.move(startPosition, endPosition);

        printCurrentScore(game);
        return isFinishedGame(game);
    }

    private boolean consentEndGame(Game game) {
        String consentInput = InputView.consentEnd();

        if(consentInput.equals(END_TEXT)) {
            printCurrentScore(game);
            printScoreWinner(game);
            return false;
        }

        return executeTurn(game);
    }

    private void printScoreWinner(Game game) {
        SideScore score = game.getCurrentSideScore();
        if(score.cho() > score.han()) {
            OutputView.printWinner(Side.CHO);
            return;
        }
        OutputView.printWinner(Side.HAN);
    }


    private void printCurrentScore(Game game) {
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
