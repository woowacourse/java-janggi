package janggiexception;

public class NotExistOnlyOneHurdleException extends RuntimeException {

    public NotExistOnlyOneHurdleException() {
        super("경로에 장애물이 1개 존재해야 합니다.");
    }
}
