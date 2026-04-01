package janggi.domain.mouveRule;

import janggi.domain.Direction;
import janggi.domain.board.BoardView;
import janggi.domain.vo.position.Path;
import janggi.domain.vo.position.Position;

import java.util.ArrayList;
import java.util.List;

public class KingMoveRule implements MoveRule {
    private static final List<Direction> KING_PATHS = List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

    @Override
    public boolean canMove(Position from, Position to, BoardView board) {
        return Path.candidatePositions(from, KING_PATHS).contains(to);
    }
}
