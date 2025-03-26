package janggi.controller;

import janggi.model.Board;
import janggi.model.BoardInitializer;
import janggi.model.Parser;
import janggi.model.Position;
import janggi.model.Turn;
import janggi.view.InputView;
import janggi.view.OutputView;
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
        String command = inputView.inputMovePositions(turn.getCurrentTurn().name());
        if (command.equals("Q")) {
            return;
        }
        List<Position> positions = Parser.parsePositions(command);
        Position startPosition = positions.get(0);
        Position endPosition = positions.get(1);
        board.move(startPosition, endPosition, turn.getCurrentTurn());
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
