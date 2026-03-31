package domain.piece;

public interface CannonRule {
    boolean canJumpOver(Piece other);

    boolean canCaptureDest(Piece dest);

    int requiredJumpCount();
}
