package piece;

import board.Board;
import movement.Movement;
import position.Position;
import position.PositionFactory;
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
        List<Position> allPositions = getAllPositions(dest);
        validateExistNode(allPositions);
        List<Position> internalPositions = List.of(allPositions.removeLast());
        validateNonObstruction(board, internalPositions);
    }

    private void validateExistNode(List<Position> allPositions) {
        for (int i = 0; i < allPositions.size() - 1; i++) {
            PositionFactory.validateAdjacentPositionBy(allPositions.get(i), allPositions.get(i + 1));
        }
    }

    private void validateNonObstruction(Board board, List<Position> internalPositions) {
        validateObstruction(board, internalPositions, EXPECTED_INTERNAL_POSITION_COUNT);
    }

    private List<Position> getAllPositions(Position destination) {
        Movement movement = Movement.findByPositions(position, destination);

        List<Position> positions = new ArrayList<>();
        Position buffer = position;
        positions.add(buffer);
        while (buffer.x() != destination.x() || buffer.y() != destination.y()) {
            buffer = buffer.move(movement);
            positions.add(buffer);
        }
        return positions;
    }

    @Override
    public BiPredicate<Position, Position> directionRule() {
        return (src, dest) -> src.isSameLine(dest) || src.isDiagonal(dest);
    }

    @Override
    public boolean equalsType(final Piece piece) {
        return piece instanceof Chariot;
    }

    @Override
    public int getScore() {
        return 13;
    }
}
