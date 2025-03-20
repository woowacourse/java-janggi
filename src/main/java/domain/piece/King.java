package domain.piece;

import java.util.Set;
import java.util.stream.Collectors;

import domain.Board;
import domain.Direction;
import domain.Position;
import domain.Team;

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

    @Override
    public String getDisplayName() {
        return "궁";
    }

    private boolean isMovable(final Position position) {
        return !board.isExists(position) || !board.isSameTeam(this, position);
    }

}
