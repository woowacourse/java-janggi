package domain.janggi.piece;

import domain.janggi.domain.Board;
import domain.janggi.domain.Color;
import domain.janggi.domain.Direction;
import domain.janggi.domain.Position;
import java.util.Set;
import java.util.stream.Collectors;

public class Solider extends Piece {

    public Solider(final Position position, final Color color, final Board board) {
        super(position, color, board);
    }

    @Override
    public Set<Position> getMovablePositions() {
        return Direction.getStraightDirection().stream()
                .filter(direction -> getUnmovableDirection() != direction)
                .filter(direction -> position.canMove(direction))
                .map(direction -> position.moveByDirection(direction))
                .filter(this::isMovable)
                .collect(Collectors.toSet());
    }

    @Override
    public String getDisplayName() {
        return "졸";
    }

    private Direction getUnmovableDirection() {
        if (color == Color.BLUE) {
            return Direction.BOTTOM;
        }
        return Direction.TOP;
    }

    private boolean isMovable(final Position position) {
        return !board.isExists(position) || !board.anyMatchSameTeam(this, position);
    }

}
