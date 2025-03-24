package piece;

import java.util.HashSet;
import java.util.Set;

import board.Board;
import board.Position;

public class Cannon extends Piece {

    public Cannon(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    protected Set<Position> getMovablePositions(final Board board) {
        Set<Position> positions = new HashSet<>();
        Direction.getStraightDirection().forEach(direction -> goOneSide(
                position.nextPosition(direction),
                direction,
                false,
                positions,
                board
        ));
        return positions;
    }

    @Override
    public String getDisplayName() {
        return "포";
    }

    private void goOneSide(Position position, Direction direction, boolean hasHuddle, Set<Position> positions,
                           final Board board) {
        if (exitCondition(position, hasHuddle, board)) {
            return;
        }
        if (!hasHuddle) {
            goOneSide(position.nextPosition(direction), direction, board.isExists(position), positions, board);
            return;
        }
        if (!board.isExists(position)) {
            goOneSide(position.nextPosition(direction), direction, true, positions, board);
        }
        positions.add(position);
    }

    private boolean exitCondition(Position position, boolean hasHuddle, final Board board) {
        return (
                position.isInValidPosition() ||
                        board.isCannonAt(position) ||
                        (board.isSameTeam(this, position) && hasHuddle)
        );
    }

}
