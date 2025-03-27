package domain.piece;

import domain.board.MovePath;
import domain.board.Position;
import domain.rule.DefaultMoveRule;
import java.util.List;

public class Soldier extends Piece {

    public static final int SOLDIER_MOVE = 1;

    public Soldier(PieceColor color) {
        super(PieceType.SOLDIER, color, DefaultMoveRule.getInstance());
    }

    @Override
    public boolean isValidMovement(MovePath movePath) {
        if (isWrongDirection(movePath)) {
            return false;
        }
        return movePath.isStraightMoveBy(SOLDIER_MOVE) || movePath.isDiagonalMoveBy(SOLDIER_MOVE);
    }

    @Override
    public List<Position> findAllRoute(MovePath movePath) {
        return movePath.getBetweenPositions();
    }

    private boolean isWrongDirection(MovePath movepath) {
        if (color == PieceColor.RED) {
            return movepath.isUpward();
        }
        if (color == PieceColor.BLUE) {
            return movepath.isDownward();
        }
        return false;
    }
}
