package piece;

import board.Board;
import board.Position;

public class General extends Piece{

    public General(final TeamType teamType) {
        super(teamType);
    }

    @Override
    public boolean canMove(final Position src, final Position destination, final Board board) {
        return src.calculateDistance(destination) == 1;
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
        return piece instanceof General;
    }
}
