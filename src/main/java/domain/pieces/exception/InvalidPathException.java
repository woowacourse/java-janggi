package domain.pieces.exception;

import domain.exception.JanggiException;

public class InvalidPathException extends JanggiException {

    public InvalidPathException(PieceErrorMessage errorMessage) {
        super(errorMessage.message());
    }
}
