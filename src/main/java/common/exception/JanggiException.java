package common.exception;

public abstract class JanggiException extends RuntimeException{

    private static final String ERROR_MESSAGE_PREFIX = "[ERROR] ";

    protected JanggiException(ExceptionInformation exceptionInformation) {
        super(ERROR_MESSAGE_PREFIX + exceptionInformation.getErrorMessage());
    }

}
