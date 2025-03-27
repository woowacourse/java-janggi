package domain.piece;

import domain.board.MovePath;
import domain.board.Position;
import domain.rule.DefaultMoveRule;
import java.util.List;

public class General extends Piece {

    public static final int GENERAL_STRAIGHT_MOVE = 1;

    public General(PieceColor color) {
        super(PieceType.GENERAL, color, DefaultMoveRule.getInstance());
    }

    @Override
    public boolean isValidMovement(MovePath movePath) {
        return movePath.isStraightMoveBy(GENERAL_STRAIGHT_MOVE);
    }

    @Override
    public List<Position> findAllRoute(MovePath movePath) {
        return movePath.getBetweenPositions();
    }
}
