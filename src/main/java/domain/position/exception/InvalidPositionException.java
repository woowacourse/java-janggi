package domain.position.exception;

import domain.exception.JanggiException;

public class InvalidPositionException extends JanggiException {

    public InvalidPositionException(PositionErrorMessage errorMessage) {
        super(errorMessage.message());
    }
}
