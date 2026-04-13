package domain.pieces.exception;

import domain.exception.JanggiException;

public class InvalidMoveException extends JanggiException {

    public InvalidMoveException(PieceErrorMessage errorMessage) {
        super(errorMessage.message());
    }
}
