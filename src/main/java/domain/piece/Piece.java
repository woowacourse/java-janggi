package domain.piece;

import domain.position.Position;
import java.util.List;

public interface Piece {
    boolean canMove(Position source, Position target);

    boolean isNotEmpty();

    boolean isAlly(Piece other);

    String display(PieceAppearance colorizer);

    List<Position> searchRoute(Position src, Position dest);

    default boolean isCannon() {
        return false;
    }
}
