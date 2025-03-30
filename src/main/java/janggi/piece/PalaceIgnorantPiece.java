package janggi.piece;

import janggi.board.palace.Palace;
import janggi.position.Position;
import java.util.Map;

public abstract class PalaceIgnorantPiece extends Piece {

    protected PalaceIgnorantPiece(final PieceType pieceType, final Team team) {
        super(pieceType, team);
    }

    public void moveTo(final Position currentPosition, final Position targetPosition,
                       final Map<Position, Piece> janggiBoard, final Palace palace) {
        canMoveBy(currentPosition, targetPosition);
        validateTeam(janggiBoard.get(targetPosition));
        checkObstacle(currentPosition, targetPosition, janggiBoard);
    }

    protected abstract void canMoveBy(final Position currentPosition, final Position targetPosition);
}
