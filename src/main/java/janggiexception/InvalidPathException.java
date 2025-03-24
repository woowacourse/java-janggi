package janggiexception;

public class InvalidPathException extends RuntimeException {

    public InvalidPathException() {
        super("해당 말은 해당 경로로 이동할 수 없습니다.");
    }
}
