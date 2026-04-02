package janggi.exception.input;

public class EmptyCoordinateException extends InputException {
    public EmptyCoordinateException() {
        super("좌표 값 중 하나가 비어 있습니다.");
    }
}
