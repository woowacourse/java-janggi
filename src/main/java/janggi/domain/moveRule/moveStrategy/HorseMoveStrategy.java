package janggi.domain.moveRule.moveStrategy;

import janggi.domain.board.Direction;
import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.piece.TeamColor;
import java.util.List;

public class HorseMoveStrategy implements MoveStrategy {
    private static final MoveStrategy INSTANCE = new HorseMoveStrategy();

    private HorseMoveStrategy() {
    }

    public static MoveStrategy getInstance() {
        return INSTANCE;
    }

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
    public List<Position> findAllRoute(PiecePath path) {
        Direction direction = Direction.from(path.rowDifference() / 2, path.columnDifference() / 2);
        return path.tracePositionsByDirection(List.of(direction));
    }
}
