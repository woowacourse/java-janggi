package piece;

import board.Board;
import board.Position;

public class Horse extends Piece {

    public static final double HORSE_DISTANCE = Math.sqrt(5);

    public Horse(final TeamType teamType) {
        super(teamType);
    }

    @Override
    protected boolean withInDirection(Position src, Position destination) {
        return true;
    }

    @Override
    protected boolean withInRangeByMovement(double distanceByPositions) {
        return distanceByPositions == HORSE_DISTANCE;
    }

    @Override
    protected boolean passFilter(Position src, Position destination, Board board) {
        final Position position = src.calculateHorseMiddlePosition(destination);
        if (board.existPieceByPosition(position)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equalsType(final Piece piece) {
        return piece instanceof Horse;
    }
}
