package janggiexception;

public class HurdleExistException extends RuntimeException {

    public HurdleExistException() {
        super("경로에 장애물이 존재합니다.");
    }
}
