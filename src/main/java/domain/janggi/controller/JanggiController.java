package domain.janggi.controller;

import domain.janggi.domain.Board;
import domain.janggi.domain.BoardInitializer;
import domain.janggi.domain.Parser;
import domain.janggi.domain.Position;
import domain.janggi.domain.Turn;
import domain.janggi.piece.Piece;
import domain.janggi.view.InputView;
import domain.janggi.view.OutputView;
import java.util.List;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        BoardInitializer boardInitializer = new BoardInitializer();
        Board board = boardInitializer.init();
        Turn turn = new Turn();
        outputView.printBoard(board);
        retry(() -> playGame(board, turn));
    }

    private void playGame(final Board board, final Turn turn) {
        String command = inputView.inputMovePositions();
        if (command.equals("Q")) {
            return;
        }
        List<Position> positions = Parser.parsePositions(command);
        Position startPosition = positions.get(0);
        Position endPosition = positions.get(1);
        Piece piece = board.findPiece(startPosition);
        piece.move(endPosition);
        turn.increaseRound();
        outputView.printBoard(board);
        playGame(board, turn);
    }

    private void retry(final Runnable runnable) {
        while (true) {
            try {
                runnable.run();
                return;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            } catch (Exception e) {
                outputView.printError("예상치 못한 예외가 발생했습니다.");
            }
        }
    }
}
