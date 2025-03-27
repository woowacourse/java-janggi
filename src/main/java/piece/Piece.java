package piece;

import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;

public interface Piece {

    boolean isSamePosition(Position startPosition);

    void updateChessPiecePositionBy(Position destination);

    PieceType getPieceType();

    void canMoveTo(final Position position);

    Positions makeRoute(final Position position);

    boolean isJanggun();

    boolean isPo();

    Position currentPosition();
}
