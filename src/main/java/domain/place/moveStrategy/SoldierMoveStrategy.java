package domain.place.moveStrategy;

import domain.board.BoardView;
import domain.place.piece.Side;
import domain.position.Position;
import java.util.List;

public class SoldierMoveStrategy implements MoveStrategy {

    private final List<Direction> directions;

    public SoldierMoveStrategy(Side side) {
        this.directions = initDirections(side);
    }

    private List<Direction> initDirections(Side side) {
        if (side == Side.CHO) {
            return List.of(Direction.DOWN, Direction.LEFT, Direction.RIGHT);
        }
        return List.of(Direction.TOP, Direction.LEFT, Direction.RIGHT);
    }

    @Override
    public boolean canMove(BoardView board, Position from, Position to) {
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
                .map(from::move)
                .anyMatch(to::equals);
    }
}
