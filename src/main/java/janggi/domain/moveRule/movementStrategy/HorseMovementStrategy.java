package janggi.domain.moveRule.movementStrategy;

import janggi.domain.board.Direction;
import janggi.domain.board.Movement;
import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.piece.TeamColor;
import java.util.List;

public class HorseMovementStrategy implements MovementStrategy {
    @Override
    public boolean isValidMovement(PiecePath path, TeamColor teamColor) {
        int rowDifference = path.rowDifference();
        int columnDifference = path.columnDifference();

        if (Math.abs(rowDifference) == 2 && Math.abs(columnDifference) == 1) {
            return true;
        }
        if (Math.abs(rowDifference) == 1 && Math.abs(columnDifference) == 2) {
            return true;
        }
        return false;
    }

    @Override
    public List<Position> findAllIntermediatePositions(PiecePath path) {
        Direction direction = Direction.from(path.rowDifference() / 2, path.columnDifference() / 2);
        return path.tracePositionsByDirection(Movement.from(direction));
    }
}
