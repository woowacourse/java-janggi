package piece;

import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;

public abstract class Piece {

    public abstract boolean isSamePosition(Position startPosition);

    public abstract void updateChessPiecePositionBy(Position destination);

    public abstract PieceType getPieceType();

    public abstract void canMoveTo(final Position position);

    public abstract Positions makeRoute(final Position position);

    public abstract boolean isJanggun();

    public abstract boolean isPo();

    public abstract Position currentPosition();

}
