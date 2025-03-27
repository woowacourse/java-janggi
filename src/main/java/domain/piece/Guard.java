package domain.piece;

import domain.board.MovePath;
import domain.board.Position;
import domain.rule.DefaultMoveRule;
import java.util.List;

public class Guard extends Piece {

    public static final int GUARD_MOVE = 1;

    public Guard(PieceColor color) {
        super(PieceType.GUARD, color, DefaultMoveRule.getInstance());
    }

    @Override
    public boolean isValidMovement(MovePath movePath) {
        if (movePath.isOutsidePalace()) {
            return false;
        }
        return movePath.isStraightMoveBy(GUARD_MOVE) || movePath.isDiagonalMoveBy(GUARD_MOVE);
    }

    @Override
    public List<Position> findAllRoute(MovePath movePath) {
        return movePath.getBetweenPositions();
    }
}
