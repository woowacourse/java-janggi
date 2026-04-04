package domain.movement.exception;

import domain.exception.JanggiException;

public class InvalidDirectionSequenceException extends JanggiException {

    public InvalidDirectionSequenceException(MovementErrorMessage errorMessage) {
        super(errorMessage.message());
    }
}
