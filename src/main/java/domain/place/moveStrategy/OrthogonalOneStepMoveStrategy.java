package domain.place.moveStrategy;

import domain.board.BoardView;
import domain.position.Position;
import java.util.List;

public abstract class OrthogonalOneStepMoveStrategy implements MoveStrategy {

    private final List<Direction> ORTHOGONAL_DIRECTIONS = List.of(
            Direction.DOWN, Direction.LEFT, Direction.RIGHT, Direction.TOP
    );

    @Override
    public boolean canMove(BoardView board, Position from, Position to) {
        if (!board.isInPalace(from) || !board.isInPalace(to)) {
            return false;
        }

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

    private boolean canReachPalaceNextPosition(BoardView board, Position from, Position to) {
        return board.findPalaceNextPositions(from).contains(to);
    }
}
