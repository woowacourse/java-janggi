package domain.movement.exception;

import domain.exception.JanggiException;

public class EmptyDirectionSequenceResultException extends JanggiException {

    public EmptyDirectionSequenceResultException(MovementErrorMessage errorMessage) {
        super(errorMessage.message());
    }
}
