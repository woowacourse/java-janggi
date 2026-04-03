package common.exception;

public abstract class JanggiException extends RuntimeException {

    protected JanggiException(String errorMessage) {
        super(errorMessage);
    }

}
