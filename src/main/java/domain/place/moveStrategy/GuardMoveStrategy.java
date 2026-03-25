package domain.place.moveStrategy;

import domain.board.Board;
import domain.position.Position;
import java.util.List;

public class GuardMoveStrategy implements MoveStrategy {

    private static final List<Direction> directions = List.of(
            Direction.DOWN, Direction.LEFT, Direction.RIGHT, Direction.TOP
    );

    @Override
    public boolean canMove(Board board, Position from, Position to) {
        if (board.isSameTeam(from, to)) {
            return false;
        }

        return canReachAdjacentPosition(from, to);
    }

    private boolean canReachAdjacentPosition(Position from, Position to) {
        int currentRow = from.getRow();
        int currentColumn = from.getColumn();

        return directions.stream().
                anyMatch(d -> isSameAsNextPosition(to, d, currentRow, currentColumn));
    }

    private static boolean isSameAsNextPosition(Position to, Direction direction, int currentRow, int currentColumn) {
        int nextRow = currentRow + direction.getRow();
        int nextColumn = currentColumn + direction.getColumn();

        Position nextPosition = new Position(nextRow, nextColumn);

        if (to.equals(nextPosition)) {
            return true;
        }
        return false;
    }
}