package domain.piece;

import domain.board.MovePath;
import domain.board.Position;
import domain.rule.DefaultMoveRule;
import java.util.List;

public class Elephant extends Piece {

    public static final int ELEPHANT_STRAIGHT_MOVE = 1;
    public static final int ELEPHANT_DIAGONAL_MOVE = 2;

    public Elephant(PieceColor color) {
        super(PieceType.ELEPHANT, color, DefaultMoveRule.getInstance());
    }

    @Override
    public boolean isValidMovement(MovePath movePath) {
        return movePath.isStraightAndDiagonalMoveBy(ELEPHANT_STRAIGHT_MOVE, ELEPHANT_DIAGONAL_MOVE);
    }

    @Override
    public List<Position> findAllRoute(MovePath movePath) {
        Position firstPosition = movePath.getPositionByFraction(3);
        MovePath firstToDestination = new MovePath(firstPosition, movePath.getDestination());
        Position secondPosition = firstToDestination.getPositionByFraction(2);

        return List.of(firstPosition, secondPosition);
    }
}
