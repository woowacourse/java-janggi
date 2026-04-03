package janggi.domain.mouveRule;

import janggi.domain.Direction;
import janggi.domain.board.BoardView;
import janggi.domain.vo.position.Path;
import janggi.domain.vo.position.Position;

import java.util.List;

public class AdvisorMoveRule implements MoveRule {
    private static final List<Direction> ADVISOR_PATHS = List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

    @Override
    public boolean canMove(Position from, Position to, BoardView board) {
        return Path.createByDirections(from, ADVISOR_PATHS).contains(to);
    }
}
