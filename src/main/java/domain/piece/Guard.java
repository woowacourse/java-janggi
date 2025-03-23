package domain.piece;

import domain.Board;
import domain.Color;
import domain.Direction;
import domain.Position;
import java.util.Set;
import java.util.stream.Collectors;

public class Guard extends Piece {

    public Guard(final Position position, final Color color, final Board board) {
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
        return "사";
    }

    private boolean isMovable(final Position position) {
        return !board.isExists(position) || !board.isSameTeam(this, position);
    }

}
