package piece;

import board.Board;
import board.Position;
import java.util.List;

public class Elephant extends Piece{

    public Elephant(final TeamType teamType) {
        super(teamType);
    }

    @Override
    protected boolean withInDirection(Position src, Position destination) {
        return true;
    }

    @Override
    protected boolean withInRangeByMovement(double distanceByPositions) {
        return distanceByPositions == Math.sqrt(13);
    }

    @Override
    protected boolean passFilter(Position src, Position destination, Board board) {
        final List<Position> positions = src.calculateElephantMiddlePositions(destination);
        for (final Position position : positions) {
            if (board.existPieceByPosition(position)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equalsType(final Piece piece) {
        return piece instanceof Elephant;
    }
}
