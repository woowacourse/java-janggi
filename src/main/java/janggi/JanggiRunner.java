package janggi;

import janggi.domain.JanggiGame;
import janggi.domain.Position;
import janggi.util.ActionExecutor;
import janggi.util.DelimiterParser;
import janggi.view.OutputView;
import janggi.view.input.InputView;
import java.util.List;

public class JanggiRunner {

    private final InputView inputView;

    public JanggiRunner(InputView inputView) {
        this.inputView = inputView;
    }

    public void execute() {
        OutputView.printStartMessage();

        JanggiGame janggiGame = JanggiGame.createInitialJanggiGame();
        while (true) {
            OutputView.printBoard(janggiGame.makeCurrentTurnBoardSnapShot());
            Position startPosition = ActionExecutor.retryUntilSuccess(
                () -> readValidStartPosition(janggiGame)
            );
            Position endPosition = ActionExecutor.retryUntilSuccess(
                () -> readValidEndPosition(janggiGame, startPosition)
            );
            janggiGame.doGame(startPosition, endPosition);
        }
    }

    private Position readValidStartPosition(JanggiGame janggiGame) {
        OutputView.printTurnNotice(janggiGame.getCurrentTurnTeamName());
        OutputView.printAskPiecePosition();
        String rawPiecePosition = inputView.readLine();
        List<String> parsedPiecePosition = DelimiterParser.parse(rawPiecePosition);
        Position startPosition = Position.makePosition(parsedPiecePosition);
        janggiGame.validatePieceExist(startPosition);
        return startPosition;
    }

    private Position readValidEndPosition(JanggiGame janggiGame, Position startPosition) {
        OutputView.printAskMovePosition(janggiGame.findPiece(startPosition).nickname());
        String rawMovePosition = inputView.readLine();
        List<String> parsedMovePosition = DelimiterParser.parse(rawMovePosition);
        Position endPosition = Position.makePosition(parsedMovePosition);
        janggiGame.validateValidEndPosition(startPosition, endPosition);
        return endPosition;
    }
}
