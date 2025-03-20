package domain.piece;

import java.util.HashSet;
import java.util.Set;

import domain.Board;
import domain.Direction;
import domain.Position;
import domain.Team;

public class Cannon extends Piece {

    public Cannon(final Position position, final Team team, final Board board) {
        super(position, team, board);
    }

    @Override
    protected Set<Position> getMovablePositions() {
        Set<Position> positions = new HashSet<>();
        Direction.getStraightDirection().forEach(direction ->
                goOneSide(position.nextPosition(direction), direction, false, positions));
        return positions;
    }

    @Override
    public String getDisplayName() {
        return "포";
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
