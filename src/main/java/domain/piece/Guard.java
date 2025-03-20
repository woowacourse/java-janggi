package domain.piece;

import domain.Board;
import domain.Direction;
import domain.Position;
import domain.Team;
import java.util.Set;
import java.util.stream.Collectors;

public class Guard extends Piece {

    public Guard(final Position position, final Team team, final Board board) {
        super(position, team, board);
    }

    @Override
    protected Set<Position> getMovablePositions() {
        return Direction.getStraightDirection().stream()
                .map(direction -> position.nextPosition(direction))
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
