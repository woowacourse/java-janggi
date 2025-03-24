package janggiexception;

public class CannotCapture포Exception extends RuntimeException {

    public CannotCapture포Exception() {
        super("포는 포를 잡을 수 없습니다.");
    }
}
