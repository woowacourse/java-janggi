package domain.piece;

import domain.board.Board;
import domain.movement.MovePath;
import domain.movement.MovePaths;
import domain.movement.Movement;
import domain.position.Position;
import validator.DistanceCheckable;
import validator.ObstructionCheckable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Horse extends Piece implements DistanceCheckable, ObstructionCheckable {

    private static final int EXPECTED_INTERNAL_POSITION_COUNT = 0;
    private static final MovePaths movePaths;
    private static final double DISTANCE;
    private static final int HORSE_SCORE = 5;

    static {
        movePaths = new MovePaths(Set.of(
                new MovePath(Movement.UP, Movement.RIGHT_UP),
                new MovePath(Movement.UP, Movement.LEFT_UP),
                new MovePath(Movement.DOWN, Movement.LEFT_DOWN),
                new MovePath(Movement.DOWN, Movement.RIGHT_DOWN),
                new MovePath(Movement.LEFT, Movement.LEFT_UP),
                new MovePath(Movement.LEFT, Movement.LEFT_DOWN),
                new MovePath(Movement.RIGHT, Movement.RIGHT_UP),
                new MovePath(Movement.RIGHT, Movement.RIGHT_DOWN)
        ));

        DISTANCE = movePaths.calculateDistance();
    }

    public Horse(final Position position, final Country country) {
        super(position, country);
    }

    @Override
    public void validateMoveCondition(Position src, Position dest, Board board) {
        validateDistance(src, dest);
        List<Position> internalPositions = getInternalPositions(getInternalMovePaths(findCorrectMovePath(dest)));
        validateNonObstruction(board, internalPositions);
    }

    private void validateNonObstruction(Board board, List<Position> internalPositions) {
        validateObstruction(board, internalPositions, EXPECTED_INTERNAL_POSITION_COUNT);
    }

    private List<Position> getInternalPositions(MovePaths internalMoverPaths) {
        List<Position> positions = new ArrayList<>();
        for (MovePath movePath : internalMoverPaths.getMovePaths()) {
            Position buffer = new Position(position.x(), position.y());
            for (Movement movement : movePath.getMovements()) {
                buffer = buffer.move(movement);
            }
            positions.add(buffer);
        }
        return positions;
    }

    private MovePaths getInternalMovePaths(MovePath movePath) {
        return movePath.getInternalMovements();
    }

    private MovePath findCorrectMovePath(Position destination) {
        return movePaths.findCorrectMovePath(position, destination);
    }

    @Override
    public double getExpectedDistance() {
        return DISTANCE;
    }

    @Override
    public boolean equalsType(final Piece piece) {
        return piece instanceof Horse;
    }

    @Override
    public int getScore() {
        return HORSE_SCORE;
    }
}
