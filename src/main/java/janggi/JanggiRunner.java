package janggi;

import janggi.domain.JanggiGame;
import janggi.domain.Position;
import janggi.util.ActionExecutor;
import janggi.util.DelimiterParser;
import janggi.view.input.InputView;
import janggi.view.output.OutputView;
import java.util.List;

public class JanggiRunner {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiRunner(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void execute() {
        outputView.printStartMessage();

        JanggiGame janggiGame = JanggiGame.createInitialJanggiGame();
        while (true) {
            outputView.printBoard(janggiGame.makeCurrentTurnBoardSnapShot());
            Position startPosition = ActionExecutor.retryUntilSuccess(
                () -> readValidStartPosition(janggiGame), outputView
            );
            Position endPosition = ActionExecutor.retryUntilSuccess(
                () -> readValidEndPosition(janggiGame, startPosition), outputView
            );
            janggiGame.doGame(startPosition, endPosition);
            if (janggiGame.isGameOver()) {
                outputView.printBoard(janggiGame.makeCurrentTurnBoardSnapShot());
                outputView.printWinner(janggiGame.findWinner()
                    .orElseThrow(() -> new IllegalStateException("승자가 존재하지 않습니다.")));
                return;
            }
        }
    }

    private Position readValidStartPosition(JanggiGame janggiGame) {
        outputView.printTurnNotice(janggiGame.getCurrentTurnTeam());
        outputView.printAskPiecePosition();
        String rawPiecePosition = inputView.readLine();
        List<String> parsedPiecePosition = DelimiterParser.parse(rawPiecePosition);
        Position startPosition = Position.makePosition(parsedPiecePosition);
        janggiGame.validatePieceExist(startPosition);
        return startPosition;
    }

    private Position readValidEndPosition(JanggiGame janggiGame, Position startPosition) {
        outputView.printAskMovePosition(janggiGame.findPiece(startPosition).nickname());
        String rawMovePosition = inputView.readLine();
        List<String> parsedMovePosition = DelimiterParser.parse(rawMovePosition);
        Position endPosition = Position.makePosition(parsedMovePosition);
        janggiGame.validateValidEndPosition(startPosition, endPosition);
        return endPosition;
    }
}
