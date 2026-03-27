package janggi;

import janggi.domain.JanggiGame;
import janggi.domain.Position;
import janggi.util.DelimiterParser;
import janggi.util.ExceptionHandler;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;

public class JanggiRunner {

    public void execute() {
        OutputView.printStartMessage();

        JanggiGame janggiGame = JanggiGame.createInitialJanggiGame();
        while (true) {
            OutputView.printBoard(janggiGame.makeCurrentTurnBoardSnapShot());
            Position startPosition = ExceptionHandler.retryUntilSuccess(
                () -> readValidStartPosition(janggiGame)
            );
            Position endPosition = ExceptionHandler.retryUntilSuccess(
                () -> readValidEndPosition(janggiGame, startPosition)
            );
            janggiGame.doGame(startPosition, endPosition);
        }
    }

    private Position readValidStartPosition(JanggiGame janggiGame) {
        OutputView.printTurnNotice(janggiGame.getCurrentTurnTeamName());
        OutputView.printAskPiecePosition();
        String rawPiecePosition = InputView.readLine();
        List<String> parsedPiecePosition = DelimiterParser.parse(rawPiecePosition);
        Position startPosition = Position.makePosition(parsedPiecePosition);
        janggiGame.validatePieceExist(startPosition);
        return startPosition;
    }

    private Position readValidEndPosition(JanggiGame janggiGame, Position startPosition) {
        OutputView.printAskMovePosition(janggiGame.findPiece(startPosition).nickname());
        String rawMovePosition = InputView.readLine();
        List<String> parsedMovePosition = DelimiterParser.parse(rawMovePosition);
        Position endPosition = Position.makePosition(parsedMovePosition);
        janggiGame.validateValidEndPosition(startPosition, endPosition);
        return endPosition;
    }
}
