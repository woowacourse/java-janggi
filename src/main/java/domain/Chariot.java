package domain;

import java.util.HashSet;
import java.util.Set;

public class Chariot extends Piece {

    public Chariot(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public Set<Position> getMovablePositions(Board board) {
        Set<Position> positions = new HashSet<>();
        for (var direction : Direction.getStraightDirection()) {
            goOneSide(position.nextPosition(direction), direction, positions, board);
        }
        return positions;
    }

    private void goOneSide(Position position, Direction direction, Set<Position> positions, Board board) {
        if (exitCondition(position, board)) {
            return;
        }
        if (!board.isExists(position)) {
            goOneSide(position.nextPosition(direction), direction, positions, board);
        }
        positions.add(position);
    }

    private boolean exitCondition(Position position, Board board) {
        return position.isInValidPosition() || (board.isExists(position) && board.isSameTeam(this, position));
    }
}
