package janggi.domain.mouveRule;

import janggi.domain.Direction;
import janggi.domain.board.BoardView;
import janggi.domain.piece.Team;
import janggi.domain.vo.position.Path;
import janggi.domain.vo.position.Position;

import java.util.List;

public final class SoldierMoveRule implements MoveRule {
    private final List<Direction> SOLDIER_PATHS;

    public SoldierMoveRule(Team team) {
        this.SOLDIER_PATHS = List.of(Direction.forwardDirection(team), Direction.EAST, Direction.WEST);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardView board) {
        if (board.isOnDiagonalPath(from, to)){
            List<Direction> palacePaths = Direction.forwardDiagonals(SOLDIER_PATHS.get(0));
            return Path.createByDirections(from, palacePaths).contains(to);
        }

        return Path.createByDirections(from, SOLDIER_PATHS).contains(to);
    }
}
