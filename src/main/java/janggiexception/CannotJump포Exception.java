package janggiexception;

public class CannotJump포Exception extends RuntimeException {

    public CannotJump포Exception() {
        super("포는 포를 넘을 수 없습니다.");
    }
}
