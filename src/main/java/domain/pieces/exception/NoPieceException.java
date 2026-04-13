package domain.pieces.exception;

import domain.exception.JanggiException;

public class NoPieceException extends JanggiException {

    public NoPieceException(PieceErrorMessage errorMessage) {
        super(errorMessage.message());
    }
}
