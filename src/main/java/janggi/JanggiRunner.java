package janggi;

import janggi.domain.*;
import janggi.dto.BoardSpots;
import janggi.util.DelimiterParser;
import janggi.util.ActionExecutor;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;

public class JanggiRunner {

    private final JanggiGameService janggiGameService;

    public JanggiRunner(JanggiGameService janggiGameService) {
        this.janggiGameService = janggiGameService;
    }

    public void execute() {
        OutputView.printStartMessage();
        JanggiGame janggiGame = janggiGameService.initializeJanggiGame(OutputView::printResumeNotice, OutputView::printResumeGameNotice, InputView::readLine);

        while (janggiGame.isRunning()) {
            OutputView.printBoard(BoardSpots.from(janggiGame.makeCurrentTurnBoardSnapShot()));
            Position startPosition = ActionExecutor.retryUntilSuccess(() -> readValidStartPosition(janggiGame));
            Position endPosition = ActionExecutor.retryUntilSuccess(() -> readValidEndPosition(janggiGame, startPosition));
            janggiGame.move(startPosition, endPosition);
        }
        OutputView.printGameOver(janggiGame.winTeamName());
    }

    private Position readValidStartPosition(JanggiGame janggiGame) {
        OutputView.printTurnNotice(janggiGame.getCurrentTurnTeamName());
        OutputView.printAskPiecePosition();
        Position startPosition = createPosition();
        janggiGame.validatePieceExists(startPosition);
        return startPosition;
    }

    private Position readValidEndPosition(JanggiGame janggiGame, Position startPosition) {
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
