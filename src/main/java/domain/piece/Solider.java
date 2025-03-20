package domain.piece;

import domain.Board;
import domain.Direction;
import domain.Position;
import domain.Team;
import java.util.Set;
import java.util.stream.Collectors;

public class Solider extends Piece {

    public Solider(final Position position, final Team team, final Board board) {
        super(position, team, board);
    }

    @Override
    protected Set<Position> getMovablePositions() {
        return Direction.getStraightDirection().stream()
                .filter(direction -> getUnmovableDirection() != direction)
                .map(direction -> position.nextPosition(direction))
                .filter(this::isMovable)
                .collect(Collectors.toSet());
    }

    private Direction getUnmovableDirection() {
        if (team == Team.BLUE) {
            return Direction.BOTTOM;
        }
        return Direction.TOP;
    }

    private boolean isMovable(final Position position) {
        return !board.isExists(position) || !board.isSameTeam(this, position);
    }

}
