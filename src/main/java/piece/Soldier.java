package piece;

import board.Board;
import board.Position;

public class Soldier extends Piece {

    public Soldier(final TeamType teamType) {
        super(teamType);
    }

    @Override
    protected boolean withInDirection(Position src, Position destination) {
        if (teamType == TeamType.RED) {
            return src.isXLessThan(destination);
        }
        return src.isXGreaterThan(destination);
    }

    @Override
    protected boolean withInRangeByMovement(double distanceByPositions) {
        return distanceByPositions == 1;
    }

    @Override
    protected boolean passFilter(Position src, Position destination, Board board) {
        return true;
    }

    @Override
    public boolean equalsType(final Piece piece) {
        return piece instanceof Soldier;
    }
}
