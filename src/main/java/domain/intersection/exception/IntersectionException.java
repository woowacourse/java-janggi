package domain.intersection.exception;

import common.exception.ExceptionInformation;
import common.exception.JanggiException;

public class IntersectionException extends JanggiException {

    public IntersectionException(ExceptionInformation exceptionInformation) {
        super(exceptionInformation);
    }

}
