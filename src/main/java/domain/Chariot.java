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
            Position current = new Position(position.getColumn(), position.getRow());
            positions.addAll(goOneSide(direction, current));
        }
        return positions;
    }

    private Set<Position> goOneSide(Direction direction, Position current) {
        Set<Position> positions = new HashSet<>();
        while (true) {
            Position nextPosition = current.nextPosition(direction);
            if (nextPosition.isInValidPosition()) {
                break;
            }
            if (board.isExists(nextPosition)) {
                if (!board.isSameTeam(this, nextPosition)) {
                    positions.add(nextPosition);
                }
                break;
            }
            positions.add(nextPosition);
            current = nextPosition;
        }
        return positions;
    }
}
