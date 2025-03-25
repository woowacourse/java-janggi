package domain.piece;

import domain.board.Position;
import domain.rule.DefaultMoveRule;
import java.util.ArrayList;
import java.util.List;

public class Elephant extends Piece {

    public static final int ELEPHANT_STRAIGHT_MOVE = 3;
    public static final int ELEPHANT_SIDE_MOVE = 2;

    public Elephant(PieceColor color) {
        super(PieceType.ELEPHANT, color, DefaultMoveRule.getInstance());
    }

    @Override
    public boolean isValidMovement(Position source, Position destination) {
        int rowAbsDifference = Math.abs(source.rowDifference(destination));
        int columnAbsDifference = Math.abs(source.columnDifference(destination));

        return (rowAbsDifference == ELEPHANT_STRAIGHT_MOVE && columnAbsDifference == ELEPHANT_SIDE_MOVE)
                || (rowAbsDifference == ELEPHANT_SIDE_MOVE && columnAbsDifference == ELEPHANT_STRAIGHT_MOVE);
    }

    @Override
    public List<Position> findAllRoute(Position source, Position destination) {
        List<Position> positions = new ArrayList<>();

        Position firstPosition = source.getPositionByFraction(destination, 3);
        Position secondPosition = firstPosition.getPositionByFraction(destination, 2);

        positions.add(firstPosition);
        positions.add(secondPosition);
        return positions;
    }
}
