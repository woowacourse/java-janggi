package movementRule;

import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;

public interface PieceRule {

    boolean isSamePosition(Position startPosition);

    void updateChessPiecePositionBy(Position destination);

    PieceType getPieceType();

    void canMoveTo(final Position destination);

    Positions makeRoute(final Position destination);

    Position currentPosition();
}
