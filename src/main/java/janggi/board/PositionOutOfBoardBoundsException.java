package janggi.board;

public class PositionOutOfBoardBoundsException extends IllegalArgumentException {
    public PositionOutOfBoardBoundsException(String message) {
        super(message);
    }
}
