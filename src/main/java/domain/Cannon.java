package domain;

import java.util.HashSet;
import java.util.Set;

public class Cannon extends Piece {

    public Cannon(Position position, Team team, Board board) {
        super(position, team, board);
    }

    @Override
    protected Set<Position> getMovablePositions() {
        Set<Position> positions = new HashSet<>();
        for (Direction straightDirection : Direction.getStraightDirection()) {
            goOneSde(straightDirection, getPosition().nextPosition(straightDirection), false, positions);
        }
        return positions;
    }

    void goOneSde(Direction direction, Position position, boolean hasHuddle, Set<Position> positions) {
        if (position.isInValidPosition() ||
                board.isCannon(position) ||
                (board.isSameTeam(this, position) && hasHuddle)
        ) {
            return;
        }
        if (!hasHuddle) {
            goOneSde(direction, position.nextPosition(direction), board.isExists(position), positions);
            return;
        }
        if (!board.isExists(position)) {
            goOneSde(direction, position.nextPosition(direction), true, positions);
        }
        positions.add(position);
    }
}
