package domain.piece;

import domain.board.MovePath;
import domain.board.Position;
import domain.rule.DefaultMoveRule;
import java.util.List;

public class Guard extends Piece {

    public static final int GUARD_STRAIGHT_MOVE = 1;

    public Guard(PieceColor color) {
        super(PieceType.GUARD, color, DefaultMoveRule.getInstance());
    }

    @Override
    public boolean isValidMovement(MovePath movePath) {
        return movePath.isStraightMoveBy(GUARD_STRAIGHT_MOVE);
    }

    @Override
    public List<Position> findAllRoute(MovePath movePath) {
        return movePath.getBetweenPositions();
    }
}
