package domain.piece;

import domain.Board;
import domain.Color;
import domain.Direction;
import domain.Position;
import java.util.Set;
import java.util.stream.Collectors;

public class Solider extends Piece {

    public Solider(final Position position, final Color color, final Board board) {
        super(position, color, board);
    }

    @Override
    protected Set<Position> getMovablePositions() {
        return Direction.getStraightDirection().stream()
                .filter(direction -> getUnmovableDirection() != direction)
                .map(direction -> position.move(direction))
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
