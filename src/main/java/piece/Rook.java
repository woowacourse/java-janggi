package piece;

import java.util.List;
import position.Position;

public class Rook extends Piece {

    public Rook(final Country country) {
        super(PieceType.ROOK, country);
    }

    public List<Position> getPathForMoving(Position fromPosition, Position toPosition) {
        if (!fromPosition.isStraight(toPosition)) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
        return fromPosition.findStraightPositions(toPosition);
    }


}
