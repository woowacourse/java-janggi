package domain.piece;

import domain.position.Position;

public interface Piece {
    boolean canMove(Position source, Position target);

    boolean isNotEmpty();

    boolean isAlly(Piece other);

    String display(PieceAppearance colorizer);

    default boolean isCannon() {
        return false;
    }
}
