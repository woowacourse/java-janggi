package domain.piece;

import domain.board.MovePath;
import domain.board.Position;
import domain.rule.DefaultMoveRule;
import java.util.List;

public class Soldier extends Piece {

    public static final int SOLDIER_STRAIGHT_MOVE = 1;

    public Soldier(PieceColor color) {
        super(PieceType.SOLDIER, color, DefaultMoveRule.getInstance());
    }

    @Override
    public boolean isValidMovement(MovePath movePath) {
        if ((color == PieceColor.RED) && !movePath.isUpward()) {
            return movePath.isStraightMoveBy(SOLDIER_STRAIGHT_MOVE);
        }
        if ((color == PieceColor.BLUE) && !movePath.isDownward()) {
            return movePath.isStraightMoveBy(SOLDIER_STRAIGHT_MOVE);
        }
        return false;
    }

    @Override
    public List<Position> findAllRoute(MovePath movePath) {
        return movePath.getBetweenPositions();
    }
}
