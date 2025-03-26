package janggi.domain.piece;

public interface PieceView {

    Side getSide();

    Position getPosition();

    boolean isSamePosition(Position position);

    PieceType getPieceType();
}
