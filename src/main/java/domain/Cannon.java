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
        Direction.getStraightDirection().forEach(direction ->
                goOneSide(position.nextPosition(direction), direction, false, positions));
        return positions;
    }

    private void goOneSide(Position position, Direction direction, boolean hasHuddle, Set<Position> positions) {
        if (exitCondition(position, hasHuddle)) {
            return;
        }
        if (!hasHuddle) {
            goOneSide(position.nextPosition(direction), direction, board.isExists(position), positions);
            return;
        }
        if (!board.isExists(position)) {
            goOneSide(position.nextPosition(direction), direction, true, positions);
        }
        positions.add(position);
    }

    private boolean exitCondition(Position position, boolean hasHuddle) {
        return position.isInValidPosition() || board.isCannon(position) ||
                (board.isSameTeam(this, position) && hasHuddle);
    }
}
