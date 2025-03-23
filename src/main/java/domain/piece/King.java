package domain.piece;

import domain.Board;
import domain.Color;
import domain.Direction;
import domain.Position;
import java.util.Set;
import java.util.stream.Collectors;

public class King extends Piece {

    public King(final Position position, final Color color, final Board board) {
        super(position, color, board);
    }

    @Override
    protected Set<Position> getMovablePositions() {
        return Direction.getStraightDirection().stream()
                .map(direction -> position.move(direction))
                .filter(this::isMovable)
                .collect(Collectors.toSet());
    }

    @Override
    public String getDisplayName() {
        return "궁";
    }

    private boolean isMovable(final Position position) {
        return !board.isExists(position) || !board.isSameTeam(this, position);
    }

}
