package piece;

import java.util.List;
import position.Position;

public class Cannon extends Piece {
    public Cannon(final Country country) {
        super(PieceType.CANNON, country);
    }

    public List<Position> getPathForMoving(Position fromPosition, Position toPosition) {
        if (!fromPosition.isStraight(toPosition)) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
        return fromPosition.findStraightPositions(toPosition);
    }
}
