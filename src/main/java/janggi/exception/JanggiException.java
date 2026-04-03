package janggi.exception;

public class JanggiException extends RuntimeException {

    public JanggiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }

    public JanggiException(ErrorCode errorCode, String detail) {
        super(errorCode.getMessage() + detail);
    }
}
