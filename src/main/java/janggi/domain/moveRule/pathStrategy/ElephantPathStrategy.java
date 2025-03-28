package janggi.domain.moveRule.pathStrategy;

import janggi.domain.board.Direction;
import janggi.domain.board.Movement;
import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.piece.TeamColor;
import java.util.List;

public class ElephantPathStrategy implements PathStrategy{
    @Override
    public boolean isValidMovement(PiecePath path, TeamColor teamColor) {
        int rowDifference = path.rowDifference();
        int columnDifference = path.columnDifference();

        if (Math.abs(rowDifference) == 3 && Math.abs(columnDifference) == 2) {
            return true;
        }
        if (Math.abs(rowDifference) == 2 && Math.abs(columnDifference) == 3) {
            return true;
        }
        return false;
    }

    @Override
    public List<Position> findAllRoute(PiecePath path) {
        int rowDifference = path.rowDifference();
        int columnDifference = path.columnDifference();

        Direction firstDirection = Direction.from(rowDifference / 3, columnDifference / 3);
        Direction secondDirection = Direction.from(
                rowDifference / Math.abs(rowDifference),
                columnDifference / Math.abs(columnDifference)
        );
        return path.tracePositionsByDirection(Movement.from(firstDirection, secondDirection));
    }
}
