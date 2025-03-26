package piece;

import java.util.Set;
import java.util.stream.Collectors;

import board.Board;
import board.Position;

public class Guard extends Piece {

    public Guard(final Team team) {
        super(team, PieceType.GUARD);
    }

    @Override
    protected Set<Position> getMovablePositions(final Position position, final Board board) {
        return Direction.getStraightDirection().stream()
                .map(position::moveByDirection)
                .filter(movePosition -> isMovable(movePosition, board))
                .collect(Collectors.toSet());
    }

    @Override
    public PieceType getType() {
        return this.pieceType;
    }

    private boolean isMovable(final Position position, final Board board) {
        return !board.isExists(position) || !board.isSameTeamPosition(this.team, position);
    }

}
