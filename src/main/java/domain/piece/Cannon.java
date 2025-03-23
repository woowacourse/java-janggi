package domain.piece;

import domain.Board;
import domain.Color;
import domain.Direction;
import domain.Position;
import java.util.HashSet;
import java.util.Set;

public class Cannon extends Piece {

    public Cannon(final Position position, final Color color, final Board board) {
        super(position, color, board);
    }

    @Override
    protected Set<Position> getMovablePositions() {
        Set<Position> positions = new HashSet<>();
        Direction.getStraightDirection().forEach(direction -> goOneSide(
                position.move(direction),
                direction,
                false,
                positions)
        );
        return positions;
    }

    @Override
    public String getDisplayName() {
        return "포";
    }

    private void goOneSide(Position position, Direction direction, boolean hasHuddle, Set<Position> positions) {
        if (exitCondition(position, direction, hasHuddle)) {
            return;
        }
        if (!hasHuddle && position.canMove(direction)) {
            goOneSide(position.move(direction), direction, board.isExists(position), positions);
            return;
        }
        if (!board.isExists(position) && position.canMove(direction)) {
            goOneSide(position.move(direction), direction, true, positions);
        }
        positions.add(position);
    }

    private boolean exitCondition(Position position, Direction direction, boolean hasHuddle) {
        return (
                board.isCannonAt(position) ||
                (board.isSameTeam(this, position) && hasHuddle)
        );
    }

}
