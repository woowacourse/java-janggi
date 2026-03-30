package janggi;

import janggi.domain.JanggiGame;
import janggi.domain.Position;
import janggi.util.DelimiterParser;
import janggi.util.ActionExecutor;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;

public class JanggiRunner {

    private final JanggiGame janggiGame;

    public JanggiRunner() {
        this.janggiGame = JanggiGame.createInitialJanggiGame();
    }

    public void execute() {
        OutputView.printStartMessage();

        while (true) {
            OutputView.printBoard(janggiGame.makeCurrentTurnBoardSnapShot());
            Position startPosition = ActionExecutor.retryUntilSuccess(this::readValidStartPosition);
            Position endPosition = ActionExecutor.retryUntilSuccess(() -> readValidEndPosition(startPosition));
            janggiGame.doGame(startPosition, endPosition);
        }
    }

    private Position readValidStartPosition() {
        OutputView.printTurnNotice(janggiGame.getCurrentTurnTeamName());
        OutputView.printAskPiecePosition();
        String rawPiecePosition = InputView.readLine();
        List<String> parsedPiecePosition = DelimiterParser.parse(rawPiecePosition);
        Position startPosition = Position.makePosition(parsedPiecePosition);
        janggiGame.validatePieceExist(startPosition);
        return startPosition;
    }

    private Position readValidEndPosition(Position startPosition) {
        OutputView.printAskMovePosition(janggiGame.findPiece(startPosition).nickname());
        String rawMovePosition = InputView.readLine();
        List<String> parsedMovePosition = DelimiterParser.parse(rawMovePosition);
        Position endPosition = Position.makePosition(parsedMovePosition);
        janggiGame.validateValidEndPosition(startPosition, endPosition);
        return endPosition;
    }
}
