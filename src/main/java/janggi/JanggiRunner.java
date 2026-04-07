package janggi;

import janggi.domain.JanggiGame;
import janggi.domain.Position;
import janggi.util.ActionExecutor;
import janggi.util.DelimiterParser;
import janggi.view.input.InputView;
import janggi.view.output.OutputView;
import java.util.List;
import java.util.Optional;

public class JanggiRunner {

    private static final String CANCEL = "cancel";

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiRunner(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void execute() {
        outputView.printStartMessage();
        JanggiGame janggiGame = JanggiGame.createInitialJanggiGame();
        while (isGameContinue(janggiGame)) {
            outputView.printBoard(janggiGame.makeCurrentTurnBoardSnapShot());
            Position startPosition = ActionExecutor.retryUntilSuccess(
                () -> readValidStartPosition(janggiGame), outputView
            );
            Optional<Position> endPosition = ActionExecutor.retryUntilSuccess(
                () -> readValidEndPosition(janggiGame, startPosition), outputView
            );
            if (endPosition.isEmpty()) {
                outputView.printMessage("말 선택을 취소했습니다. 다시 선택해주세요.");
                continue;
            }
            janggiGame.doGame(startPosition, endPosition.get());
        }
        int winnerScore = janggiGame.getWinnerScore();
        outputView.printResult(
            janggiGame.makeCurrentTurnBoardSnapShot(),
            janggiGame.findWinner()
                .orElseThrow(() -> new IllegalStateException("승자가 존재하지 않습니다.")),
            winnerScore
        );
    }

    private boolean isGameContinue(JanggiGame janggiGame) {
        return !janggiGame.isGameOver();
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

    private Optional<Position> readValidEndPosition(JanggiGame janggiGame, Position startPosition) {
        outputView.printAskMovePosition(janggiGame.findPiece(startPosition).nickname());
        String rawMovePosition = inputView.readLine();
        if (isCancelCommand(rawMovePosition)) {
            return Optional.empty();
        }
        List<String> parsedMovePosition = DelimiterParser.parse(rawMovePosition);
        Position endPosition = Position.makePosition(parsedMovePosition);
        janggiGame.validateValidEndPosition(startPosition, endPosition);
        return Optional.of(endPosition);
    }

    private boolean isCancelCommand(String input) {
        return CANCEL.equals(input);
    }
}
