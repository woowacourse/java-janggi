package janggi;

import janggi.board.Board;
import janggi.board.BoardFactory;
import janggi.board.Position;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiManager {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiManager(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void play() {
        Board board = BoardFactory.initBoard();
        outputView.printBoard(board.getBoard());
        while (true) {
            String inputStartPosition = inputView.readStartPosition();
            if (inputStartPosition.equals("Q")) {
                break;
            }
            String inputEndPosition = inputView.readEndPosition();
            movePiece(inputStartPosition, inputEndPosition, board);
            outputView.printBoard(board.getBoard());
        }
    }

    private void movePiece(String inputStartPosition, String inputEndPosition, Board board) {
        handleException(() -> {
            Position start = parsePosition(inputStartPosition);
            Position end = parsePosition(inputEndPosition);
            board.move(start, end);
        });
    }

    private Position parsePosition(final String input) {
        String[] coordinate = input.split(",");
        int x = Integer.parseInt(coordinate[0]);
        int y = Integer.parseInt(coordinate[1]);
        return new Position(x, y);
    }

    private void handleException(final Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
        }
    }
}
