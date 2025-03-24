package piece;

import board.Board;
import board.Position;

public class Guard extends Piece {

    public Guard(final TeamType teamType) {
        super(teamType);
    }

    @Override
    protected boolean withInDirection(Position src, Position destination) {
        return true;
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
        return piece instanceof Guard;
    }
}
