package domain.movepolicy.exception;

import domain.exception.JanggiException;

public class InvalidDestinationException extends JanggiException {

    public InvalidDestinationException(MovePolicyErrorMessage errorMessage) {
        super(errorMessage.message());
    }
}
