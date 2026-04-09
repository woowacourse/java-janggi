package janggi.exception.move;

public class SamePositionException extends MoveException {
    public SamePositionException() {
        super("출발지와 목적지가 같을 수 없습니다.");
    }
}
