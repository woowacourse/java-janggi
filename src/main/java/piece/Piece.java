package piece;

import location.Position;
import store.Pieces;

public interface Piece {
    void validateDestination(Position destination);

    void validatePaths(Pieces pieces, Position destination);

    Piece move(Position destination);

    boolean isPlacedAt(Position targetPosition);

    Position getCurrentPosition();

    PieceType getPieceType();
}
