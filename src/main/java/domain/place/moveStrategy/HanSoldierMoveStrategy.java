package domain.place.moveStrategy;

import domain.board.BoardView;
import domain.position.Position;
import java.util.List;

public class HanSoldierMoveStrategy implements MoveStrategy {

    private static final List<Direction> ORTHOGONAL_DIRECTIONS = List.of(
            Direction.TOP, Direction.LEFT, Direction.RIGHT
    );

    @Override
    public boolean canMove(BoardView board, Position from, Position to) {
        return canReachAdjacentPosition(from, to) || canReachPalaceNextPosition(board, from, to);
    }

    private boolean canReachAdjacentPosition(Position from, Position to) {
        int currentRow = from.getRow();
        int currentColumn = from.getColumn();

        return ORTHOGONAL_DIRECTIONS.stream()
                .filter(d -> Position.isNotOutOfBounds(currentRow + d.getRow(), currentColumn + d.getColumn()))
                .map(from::move)
                .anyMatch(to::equals);
    }
}
