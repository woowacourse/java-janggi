package piece;

import board.Board;
import position.Position;
import validator.DirectionCheckable;
import validator.ObstructionCheckable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public class Chariot extends Piece implements DirectionCheckable, ObstructionCheckable {

    private static final int EXPECTED_INTERNAL_POSITION_COUNT = 0;

    public Chariot(final Position position, final Country country) {
        super(position, country);
    }

    @Override
    public void validateMoveCondition(Position src, Position dest, Board board) {
        validateDirection(src, dest);
        List<Position> internalPositions = getInternalPositions(dest);
        validateObstruction(board, internalPositions, EXPECTED_INTERNAL_POSITION_COUNT);
    }

    private List<Position> getInternalPositions(Position destination) {
        List<Position> positions = new ArrayList<>();
        for (int x = Math.min(position.x(), destination.x()); x <= Math.max(position.x(), destination.x()); x++) {
            for (int y = Math.min(position.y(), destination.y()); y <= Math.max(position.y(), destination.y()); y++) {
                positions.add(new Position(x, y));
            }
        }
        positions.removeLast();
        return positions;
    }

    @Override
    public BiPredicate<Position, Position> directionRule() {
        return Position::isSameLine;
    }

    @Override
    public boolean equalsType(final Piece piece) {
        return piece instanceof Chariot;
    }
}
