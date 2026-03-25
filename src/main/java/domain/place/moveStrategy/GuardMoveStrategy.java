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

        return directions.stream()
                .filter(d -> Position.isNotOutOfBounds(currentRow + d.getRow(), currentColumn + d.getColumn()))
                .map(d -> new Position(currentRow + d.getRow(), currentColumn + d.getColumn()))
                .anyMatch(to::equals);
    }
}