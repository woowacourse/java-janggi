package janggi;

import janggi.domain.JanggiGame;
import janggi.domain.Position;
import janggi.domain.WinnerResult;
import janggi.service.JanggiService;
import janggi.util.ActionExecutor;
import janggi.util.DelimiterParser;
import janggi.util.PersistenceExecutor;
import janggi.view.input.InputView;
import janggi.view.output.OutputView;
import java.util.List;
import java.util.Optional;

public class JanggiRunner {

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiService janggiService;

    public JanggiRunner(InputView inputView, OutputView outputView, JanggiService janggiService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiService = janggiService;
    }

    public void execute() {
        outputView.printStartMessage();
        JanggiGame janggiGame = PersistenceExecutor.retryOnTimeout(
            janggiService::loadGame,
            this::printPersistenceTimeoutMessage
        );
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
            PersistenceExecutor.retryOnTimeout(
                () -> janggiService.play(janggiGame, startPosition, endPosition.get()),
                this::printPersistenceTimeoutMessage
            );
        }
        finish(janggiGame);
    }

    private void finish(JanggiGame janggiGame) {
        PersistenceExecutor.retryOnTimeout(janggiService::finishGame, this::printPersistenceTimeoutMessage);
        WinnerResult winnerResult = janggiGame.getWinnerResult();
        outputView.printResult(
            janggiGame.makeCurrentTurnBoardSnapShot(),
            winnerResult.winner(),
            winnerResult.winnerScore()
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
        Optional<String> rawMovePosition = inputView.readCancelableLine();
        if (rawMovePosition.isEmpty()) {
            return Optional.empty();
        }
        List<String> parsedMovePosition = DelimiterParser.parse(rawMovePosition.get());
        Position endPosition = Position.makePosition(parsedMovePosition);
        janggiGame.validateValidEndPosition(startPosition, endPosition);
        return Optional.of(endPosition);
    }

    private void printPersistenceTimeoutMessage() {
        outputView.printMessage("데이터베이스 응답이 지연되었습니다. 다시 시도합니다.");
    }
}
