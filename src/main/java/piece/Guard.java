package piece;

import java.util.Set;
import java.util.stream.Collectors;

import board.Board;
import board.Position;

public class Guard extends Piece {

    public Guard(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    protected Set<Position> getMovablePositions(final Board board) {
        return Direction.getStraightDirection().stream()
                .map(direction -> position.moveByDirection(direction))
                .filter(position -> isMovable(position, board))
                .collect(Collectors.toSet());
    }

    @Override
    public String getDisplayName() {
        return "사";
    }

    private boolean isMovable(final Position position, final Board board) {
        return !board.isExists(position) || !board.isSameTeamPosition(this.team, position);
    }

}
