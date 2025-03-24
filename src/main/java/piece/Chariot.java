package piece;

import board.Board;
import board.Position;
import java.util.List;

public class Chariot extends Piece{

    public Chariot(final TeamType teamType) {
        super(teamType);
    }

    @Override
    protected boolean withInDirection(Position src, Position destination) {
        return src.isSameLine(destination);
    }

    @Override
    protected boolean withInRangeByMovement(double distanceByPositions) {
        return true;
    }

    @Override
    protected boolean passFilter(Position src, Position destination, Board board) {
        final List<Position> positions = src.calculateBetweenPositions(destination);
        for (final Position position : positions) {
            if (board.existPieceByPosition(position)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equalsType(final Piece piece) {
        return piece instanceof Chariot;
    }
}
