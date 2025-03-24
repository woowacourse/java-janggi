package janggiexception;

public class CannotJumpCannonException extends RuntimeException {

    public CannotJumpCannonException() {
        super("포는 포를 넘을 수 없습니다.");
    }
}
