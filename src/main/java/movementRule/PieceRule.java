package movementRule;

import pieceProperty.Position;
import pieceProperty.Positions;

public interface PieceRule {

    void canMoveTo(final Position startPosition, final Position destination);

    Positions makeRoute(final Position startPosition, final Position destination);

    int getScore();

}
