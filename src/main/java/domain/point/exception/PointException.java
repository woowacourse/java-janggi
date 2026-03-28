package domain.point.exception;

import common.exception.ExceptionInformation;
import common.exception.JanggiException;

public class PointException extends JanggiException {

    public PointException(ExceptionInformation exceptionInformation) {
        super(exceptionInformation);
    }

}
