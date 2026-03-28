package domain.move.path.exception;

import common.exception.ExceptionInformation;
import common.exception.JanggiException;

public class PathException extends JanggiException {

    public PathException(ExceptionInformation exceptionInformation) {
        super(exceptionInformation);
    }

}
