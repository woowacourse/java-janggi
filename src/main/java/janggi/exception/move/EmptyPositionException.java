package janggi.exception.move;

public class EmptyPositionException extends MoveException {
    public EmptyPositionException() {
        super("출발지에 이동할 기물이 없습니다.");
    }
}
