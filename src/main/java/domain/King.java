package domain;

import java.util.Set;
import java.util.stream.Collectors;

public class King extends Piece {

    public King(final Position position, final Team team, final Board board) {
        super(position, team, board);
    }

    @Override
    protected Set<Position> getMovablePositions() {
        return Direction.getStraightDirection().stream()
                .map(direction -> position.nextPosition(direction))
                .filter(this::isMovable)
                .collect(Collectors.toSet());
    }

    private boolean isMovable(final Position position) {
        return !board.isExists(position) || !board.isSameTeam(this, position);
    }

}
