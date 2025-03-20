package domain;

import java.util.Set;
import java.util.stream.Collectors;

public class Guard extends Piece {

    public Guard(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    protected Set<Position> getMovablePositions(Board board) {
        return Direction.getStraightDirection().stream()
                .map(direction -> position.nextPosition(direction))
                .filter(p -> isMovable(p, board))
                .collect(Collectors.toSet());
    }

    private boolean isMovable(final Position position, Board board) {
        return !board.isExists(position) || !board.isSameTeam(this, position);
    }

}
