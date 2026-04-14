package exception;

public class PieceDbIdNotFoundException extends IllegalStateException {
    public PieceDbIdNotFoundException() {
        super(ErrorMessage.PIECE_DB_ID_MISSING.getMessage());
    }
}
