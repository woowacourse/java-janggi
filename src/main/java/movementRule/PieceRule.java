package movementRule;

import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;

public interface PieceRule {

    boolean isPo();

    boolean isJanggun();

    PieceType getPieceType();

    void canMoveTo(final Position startPosition, final Position destination);

    Positions makeRoute(final Position startPosition, final Position destination);

}
