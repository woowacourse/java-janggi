package janggiexception;

public class CannotCaptureCannonException extends RuntimeException {

    public CannotCaptureCannonException() {
        super("포는 포를 잡을 수 없습니다.");
    }
}
