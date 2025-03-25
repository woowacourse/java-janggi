package domain.piece;

import domain.board.Position;
import domain.rule.DefaultMoveRule;
import java.util.List;

public class Horse extends Piece {

    public static final int HORSE_STRAIGHT_MOVE = 2;
    public static final int HORSE_SIDE_MOVE = 1;

    public Horse(PieceColor color) {
        super(PieceType.HORSE, color, DefaultMoveRule.getInstance());
    }

    @Override
    public boolean isValidMovement(Position source, Position destination) {
        int rowAbsDifference = Math.abs(source.rowDifference(destination));
        int columnAbsDifference = Math.abs(source.columnDifference(destination));

        return (rowAbsDifference == HORSE_STRAIGHT_MOVE && columnAbsDifference == HORSE_SIDE_MOVE)
                || (rowAbsDifference == HORSE_SIDE_MOVE && columnAbsDifference == HORSE_STRAIGHT_MOVE);
    }

    @Override
    public List<Position> findAllRoute(Position source, Position destination) {
        Position route = source.getPositionByFraction(destination, 2);

        return List.of(route);
    }
}
