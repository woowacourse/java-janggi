package domain.janggi.piece;

import domain.janggi.domain.Board;
import domain.janggi.domain.Color;
import domain.janggi.domain.Direction;
import domain.janggi.domain.Position;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Chariot extends Piece {

    public Chariot(final Position position, final Color color, final Board board) {
        super(position, color, board);
    }

    @Override
    public Set<Position> getMovablePositions() {
        return Direction.getStraightDirection().stream()
                .flatMap(direction -> calculateMovablePositionInDirection(direction, position).stream())
                .collect(Collectors.toSet());
    }

    private Set<Position> calculateMovablePositionInDirection(final Direction direction, final Position position) {
        Set<Position> positions = new HashSet<>();
        if (!position.canMove(direction)) {
            return positions;
        }
        Position nextPosition = position.moveByDirection(direction);
        if (!board.anyMatchSameTeam(this, nextPosition)) {
            positions.add(nextPosition);
        }
        if (board.isExists(nextPosition)) {
            return positions;
        }
        positions.addAll(calculateMovablePositionInDirection(direction, nextPosition));
        return positions;
    }

    @Override
    public String getDisplayName() {
        return "차";
    }
}
