package domain;

import java.util.HashSet;
import java.util.Set;

public class Horse extends Piece {

    public Horse(Position position, Team team, Board board) {
        super(position, team, board);
    }

    @Override
    protected Set<Position> getMovablePositions() {
        Set<Position> positions = new HashSet<>();
        for (Direction straightDirection : Direction.getStraightDirection()) {
            Position current = position.nextPosition(straightDirection);
            if (current.isInValidPosition() || board.isExists(current)) {
                continue;
            }
            for (Direction direction : straightDirection.nextCrossDirection()) {
                Position nextPosition = current.nextPosition(direction);
                if (nextPosition.isInValidPosition()) {
                    continue;
                }
                if (board.isExists(nextPosition)) {
                    if (!board.isSameTeam(this, nextPosition)) {
                        positions.add(nextPosition);
                    }
                    continue;
                }
                positions.add(nextPosition);
            }
        }
        return positions;
    }
}
