package domain.janggi.piece;

import domain.janggi.domain.Board;
import domain.janggi.domain.Color;
import domain.janggi.domain.Direction;
import domain.janggi.domain.Position;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Cannon extends Piece {

    public Cannon(final Position position, final Color color, final Board board) {
        super(position, color, board);
    }

    @Override
    public Set<Position> getMovablePositions() {
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
        Position nextPosition = position.moveByDirection(direction);
        if (isCannonAt(nextPosition)) {
            return positions;
        }
        if (meetCount == 1 && !board.anyMatchSameTeam(this, nextPosition)) {
            positions.add(nextPosition);
        }
        positions.addAll(computePositions(nextPosition, direction, computeMeetCount(meetCount, nextPosition)));
        return positions;
    }

    private boolean isCannonAt(Position position) {
        if (!board.isExists(position)) {
            return false;
        }
        return board.findPiece(position) instanceof Cannon;
    }

    private int computeMeetCount(int currentMeetCount, Position nextPosition) {
        if (board.isExists(nextPosition)) {
            return currentMeetCount + 1;
        }
        return currentMeetCount;
    }
}
