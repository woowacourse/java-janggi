package janggi;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Turn;
import janggi.domain.Turns;
import janggi.domain.side.TeamType;
import janggi.util.DelimiterParser;
import janggi.util.ExceptionHandler;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;

public class JanggiRunner {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiRunner(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void execute() {
        Board board = Board.createInitialBoard();
        outputView.printStartMessage();
        outputView.printBoard(board.makeSpots());

        Turns turns = new Turns(List.of(new Turn(TeamType.HAN, board)));
        while (true) {
            Turns currentTurns = turns;
            Position startPosition = ExceptionHandler.retryUntilSuccess(() -> readValidStartPosition(currentTurns));
            Position endPosition = ExceptionHandler.retryUntilSuccess(() -> readValidEndPosition(currentTurns, startPosition));
            turns = turns.doGame(startPosition, endPosition);
        }
    }

    private Position readValidStartPosition(Turns turns) {
        outputView.printAskPiecePosition();
        String rawPiecePosition = inputView.readLine();
        List<String> parsedPiecePosition = DelimiterParser.parse(rawPiecePosition);
        Position startPosition = Position.makePosition(parsedPiecePosition);
        turns.validatePieceExist(startPosition);
        return startPosition;
    }

    private Position readValidEndPosition(Turns turns, Position startPosition) {
        outputView.printAskMovePosition(turns.findPiece(startPosition).nickname());
        String rawMovePosition = inputView.readLine();
        List<String> parsedMovePosition = DelimiterParser.parse(rawMovePosition);
        Position endPosition = Position.makePosition(parsedMovePosition);
        turns.validateValidEndPosition(startPosition, endPosition);
        return endPosition;
    }
}
