package domain.piece;

public interface CannonRule {
    void validateJumpOver(Piece other);

    void validateCaptureDest(Piece dest);

    void validateJumpCount(int count);
}
