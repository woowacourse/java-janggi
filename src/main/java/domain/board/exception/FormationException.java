package domain.board.exception;

import common.exception.ExceptionInformation;
import common.exception.JanggiException;

public class FormationException extends JanggiException {

    public FormationException(ExceptionInformation exceptionInformation) {
        super(exceptionInformation);
    }

}
