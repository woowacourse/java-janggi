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
        if (position.isInValidPosition()) {
            return;
        }
        if (board.isExists(position)) {
            if (!board.isSameTeam(this, position)) {
                positions.add(position);
            }
            return;
        }
        positions.add(position);
        goOneSide(position.nextPosition(direction), direction, positions);
    }
}
