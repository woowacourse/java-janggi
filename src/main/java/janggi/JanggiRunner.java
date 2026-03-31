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
        Position startPosition = createPosition();
        janggiGame.validatePieceExists(startPosition);
        return startPosition;
    }

    private Position readValidEndPosition(Position startPosition) {
        OutputView.printAskMovePosition(janggiGame.getPieceName(startPosition));
        Position endPosition = createPosition();
        janggiGame.validateValidEndPosition(startPosition, endPosition);
        return endPosition;
    }

    private static Position createPosition() {
        String rawPiecePosition = InputView.readLine();
        List<String> parsedPiecePosition = DelimiterParser.parse(rawPiecePosition);
        return Position.makePosition(parsedPiecePosition);
    }
}
