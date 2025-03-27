package piece;

import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;

public interface Piece {

    boolean isSamePosition(Position startPosition);

    void updateChessPiecePositionBy(Position destination);

    PieceType getPieceType();

    void canMoveTo(final Position destination);

    Positions makeRoute(final Position destination);

    Position currentPosition();
}
