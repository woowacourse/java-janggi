package domain.board.exception;

import domain.exception.JanggiException;

public class InvalidSangSetupException extends JanggiException {

    public InvalidSangSetupException(BoardErrorMessage errorMessage) {
        super(errorMessage.message());
    }
}
