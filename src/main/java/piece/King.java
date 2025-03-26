package piece;

import java.util.Set;
import java.util.stream.Collectors;

import board.Board;
import board.Position;

public class King extends Piece {

    public King(final Team team) {
        super(team);
    }

    @Override
    protected Set<Position> getMovablePositions(final Position position, final Board board) {
        return Direction.getStraightDirection().stream()
                .map(position::moveByDirection)
                .filter(movePosition -> isMovable(movePosition, board))
                .collect(Collectors.toSet());
    }

    @Override
    public String getDisplayName() {
        return "궁";
    }

    private boolean isMovable(final Position position, final Board board) {
        return !board.isExists(position) || !board.isSameTeamPosition(this.team, position);
    }

}
