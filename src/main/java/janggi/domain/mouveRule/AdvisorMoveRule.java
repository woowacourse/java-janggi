package janggi.domain.mouveRule;

import janggi.domain.Direction;
import janggi.domain.board.BoardView;
import janggi.domain.vo.Position;

import java.util.ArrayList;
import java.util.List;

public class AdvisorMoveRule implements MoveRule {
    private static final List<Direction> ADVISOR_PATHS = List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

    @Override
    public boolean canMove(Position from, Position to, BoardView board) {
        return candidatePositions(from).contains(to);
    }

    private List<Position> candidatePositions(Position from) {
        List<Position> positions =  new ArrayList<>();

        for (Direction direction : ADVISOR_PATHS) {
            if (from.hasNext(direction)) {
                positions.add(from.nextPosition(direction));
            }
        }

        return positions;
    }
}
