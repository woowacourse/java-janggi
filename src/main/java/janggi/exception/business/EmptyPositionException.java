package janggi.exception.business;

public class EmptyPositionException extends BusinessException {
    public EmptyPositionException() {
        super("출발지에 이동할 기물이 없습니다.");
    }
}
