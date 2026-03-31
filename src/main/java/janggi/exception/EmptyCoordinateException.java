package janggi.exception;

public class EmptyCoordinateException extends BusinessException {
    public EmptyCoordinateException() {
        super("좌표 값 중 하나가 비어 있습니다.");
    }
}
