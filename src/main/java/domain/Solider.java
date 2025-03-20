package domain;

import java.util.Set;
import java.util.stream.Collectors;

public class Solider extends Piece {

    public Solider(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    protected Set<Position> getMovablePositions(Board board) {
        return Direction.getStraightDirection().stream()
                .filter(direction -> getUnmovableDirection() != direction)
                .map(direction -> position.nextPosition(direction))
                .filter(p -> isMovable(p, board))
                .collect(Collectors.toSet());
    }

    private Direction getUnmovableDirection() {
        if (team == Team.BLUE) {
            return Direction.BOTTOM;
        }
        return Direction.TOP;
    }

    private boolean isMovable(final Position position, Board board) {
        return !board.isExists(position) || !board.isSameTeam(this, position);
    }

}
