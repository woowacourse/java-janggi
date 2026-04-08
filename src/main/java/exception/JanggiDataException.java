package exception;

public class JanggiDataException extends RuntimeException {

    public JanggiDataException(String message) {
        super(message);
    }

    public JanggiDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
