package common.exception;

public abstract class JanggiException extends RuntimeException{

    protected JanggiException(ExceptionInformation exceptionInformation) {
        super(exceptionInformation.getExceptionInformation());
    }

}
