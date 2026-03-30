package janggi.domain.mouveRule;

import janggi.domain.Direction;
import janggi.domain.board.BoardView;
import janggi.domain.piece.Team;
import janggi.domain.vo.Position;

import java.util.ArrayList;
import java.util.List;

public final class SoldierMoveRule implements MoveRule {
    private final List<Direction> SOLDIER_PATHS;

    public SoldierMoveRule(Team team) {
        this.SOLDIER_PATHS = List.of(Direction.forwardDirection(team), Direction.EAST, Direction.WEST);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardView board) {
        return candidatePositions(from).contains(to);
    }

    private List<Position> candidatePositions(Position from) {
        List<Position> positions =  new ArrayList<>();

        for (Direction direction : SOLDIER_PATHS) {
            if (from.hasNext(direction)) {
                positions.add(from.nextPosition(direction));
            }
        }

        return positions;
    }
}
