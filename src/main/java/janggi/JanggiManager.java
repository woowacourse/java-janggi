package janggi;

import janggi.board.Board;
import janggi.board.Position;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiManager {

    private final InputView inputView;
    private final OutputView outputView;
    private final Board board;
    private Turn turn;

    public JanggiManager(Board board, InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.board = board;
        this.turn = Turn.firstTurn();
    }

    public void play() {
        outputView.printBoard(board.getBoard());
        while (true) {
            String inputStartPosition = inputView.readStartPosition();
            if (inputStartPosition.equals("Q")) {
                break;
            }
            String inputEndPosition = inputView.readEndPosition();
            movePiece(inputStartPosition, inputEndPosition);
            outputView.printBoard(board.getBoard());
        }
    }

    private void movePiece(final String inputStartPosition, final String inputEndPosition) {
        handleException(() -> {
            Position start = parsePosition(inputStartPosition);
            Position end = parsePosition(inputEndPosition);
            board.move(start, end, turn);
            this.turn = turn.nextTurn();
        });
    }

    private Position parsePosition(final String input) {
        String[] coordinate = input.split(",");
        try {
            int x = Integer.parseInt(coordinate[0]);
            int y = Integer.parseInt(coordinate[1]);
            return new Position(x, y);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("(x,y) 형태로 입력해주세요.");
        }
    }

    private void handleException(final Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
        }
    }
}
