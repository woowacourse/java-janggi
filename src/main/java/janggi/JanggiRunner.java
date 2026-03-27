package janggi;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Turn;
import janggi.domain.JanggiGame;
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

        JanggiGame janggiGame = new JanggiGame(List.of(new Turn(TeamType.HAN, board))); // 여기서 Turn 객체를 Turns 생성자 내부에서 생성해주는게 나을 듯?
        while (true) {
            JanggiGame currentJanggiGame = janggiGame;
            Position startPosition = ExceptionHandler.retryUntilSuccess(() -> readValidStartPosition(currentJanggiGame));
            Position endPosition = ExceptionHandler.retryUntilSuccess(() -> readValidEndPosition(currentJanggiGame, startPosition));
            janggiGame = janggiGame.doGame(startPosition, endPosition);
        }
    }

    private Position readValidStartPosition(JanggiGame janggiGame) {
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
