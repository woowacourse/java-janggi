package domain;

import java.util.HashSet;
import java.util.Set;

public class Cannon extends Piece {

    public Cannon(Position position, Team team) {
        super(position, team);
    }

    @Override
    protected Set<Position> getMovablePositions(Board board) {
        Set<Position> positions = new HashSet<>();
        Direction.getStraightDirection().forEach(direction ->
                goOneSide(position.nextPosition(direction), direction, false, positions, board));
        return positions;
    }

    private void goOneSide(
            Position position,
            Direction direction,
            boolean hasHuddle,
            Set<Position> positions,
            Board board
    ) {
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

    private boolean exitCondition(Position position, boolean hasHuddle, Board board) {
        return position.isInValidPosition() || board.isCannon(position) ||
                (board.isSameTeam(this, position) && hasHuddle);
    }

}
