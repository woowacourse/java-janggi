package domain;

import java.util.HashSet;
import java.util.Set;

public class Chariot extends Piece {

    public Chariot(final Position position, final Team team, final Board board) {
        super(position, team, board);
    }

    @Override
    public Set<Position> getMovablePositions() {
        Set<Position> positions = new HashSet<>();
        for (var direction : Direction.getStraightDirection()) {
            goOneSide(position.nextPosition(direction), direction, positions);
        }
        return positions;
    }

    private void goOneSide(Position position, Direction direction, Set<Position> positions) {
        if (exitCondition(position)) {
            return;
        }
        if (!board.isExists(position)) {
            goOneSide(position.nextPosition(direction), direction, positions);
        }
        positions.add(position);
    }

    private boolean exitCondition(Position position) {
        return position.isInValidPosition() || (board.isExists(position) && board.isSameTeam(this, position));
    }
}
