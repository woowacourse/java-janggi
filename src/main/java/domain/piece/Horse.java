package domain.piece;

import domain.board.MovePath;
import domain.board.Position;
import domain.rule.DefaultMoveRule;
import java.util.List;

public class Horse extends Piece {

    public static final int HORSE_STRAIGHT_MOVE = 1;
    public static final int HORSE_DIAGONAL_MOVE = 1;

    public Horse(PieceColor color) {
        super(PieceType.HORSE, color, DefaultMoveRule.getInstance());
    }

    @Override
    public boolean isValidMovement(MovePath movePath) {
        return movePath.isStraightAndDiagonalMoveBy(HORSE_STRAIGHT_MOVE, HORSE_DIAGONAL_MOVE);
    }

    @Override
    public List<Position> findAllRoute(MovePath movePath) {
        Position route = movePath.getPositionByFraction(2);

        return List.of(route);
    }
}
