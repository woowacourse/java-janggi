package domain.piece;

import domain.Board;
import domain.Color;
import domain.Direction;
import domain.Position;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Cannon extends Piece {

    public Cannon(final Position position, final Color color, final Board board) {
        super(position, color, board);
    }

    @Override
    protected Set<Position> getMovablePositions() {
        return Direction.getStraightDirection()
                .stream()
                .flatMap(direction -> computePositions(position, direction, 0).stream())
                .collect(Collectors.toSet());
    }

    @Override
    public String getDisplayName() {
        return "포";
    }

    private Set<Position> computePositions(Position position, Direction direction, int meetCount) {
        Set<Position> positions = new HashSet<>();
        if (!position.canMove(direction) || meetCount > 1) {
            return positions;
        }
        Position nextPosition = position.move(direction);
        if (board.isCannonAt(nextPosition)) {
            return positions;
        }
        if (meetCount == 1 && !board.anyMatchSameTeam(this, nextPosition)) {
            positions.add(nextPosition);
        }
        positions.addAll(computePositions(nextPosition, direction, computeMeetCount(meetCount, nextPosition)));
        return positions;
    }

    private int computeMeetCount(int currentMeetCount, Position nextPosition) {
        if (board.isExists(nextPosition)) {
            return currentMeetCount + 1;
        }
        return currentMeetCount;
    }
}
