package piece;

import java.util.Set;
import java.util.stream.Collectors;

import board.Board;
import board.Position;

public class Soldier extends Piece {

    public Soldier(final Team team) {
        super(team, PieceType.SOLDIER);
    }

    @Override
    protected Set<Position> getMovablePositions(final Position position, final Board board) {
        return Direction.getStraightDirection().stream()
                .filter(direction -> getUnmovableDirection() != direction)
                .map(position::moveByDirection)
                .filter(movePosition -> isMovable(movePosition, board))
                .collect(Collectors.toSet());
    }

    @Override
    public PieceType getType() {
        return this.pieceType;
    }

    private Direction getUnmovableDirection() {
        if (team == Team.BLUE) {
            return Direction.BOTTOM;
        }
        return Direction.TOP;
    }

    private boolean isMovable(final Position position, final Board board) {
        return !board.isExists(position) || !board.isSameTeamPosition(this.team, position);
    }

}
