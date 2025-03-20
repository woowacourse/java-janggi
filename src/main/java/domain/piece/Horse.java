package domain.piece;

import domain.Board;
import domain.Direction;
import domain.Position;
import domain.Team;
import java.util.HashSet;
import java.util.Set;

public class Horse extends Piece {

    public Horse(Position position, Team team, Board board) {
        super(position, team, board);
    }

    @Override
    protected Set<Position> getMovablePositions() {
        Set<Position> positions = new HashSet<>();
        Direction.getStraightDirection().forEach(direction ->
                goOneSide(position.nextPosition(direction), direction, positions, 0));
        return positions;
    }

    @Override
    public String getDisplayName() {
        return "마";
    }

    void goOneSide(Position position, Direction direction, Set<Position> positions, int moveCount) {
        if (exitCondition(position, direction, moveCount)) {
            return;
        }
        if (direction.isCrossDirection() && !board.isSameTeam(this, position)) {
            positions.add(position);
            return;
        }
        for (Direction crossDirection : direction.nextCrossDirection()) {
            goOneSide(position.nextPosition(crossDirection), crossDirection, positions, moveCount + 1);
        }
    }

    private boolean exitCondition(Position position, Direction direction, int moveCount) {
        return position.isInValidPosition() ||
                (direction.isStraightDirection() && board.isExists(position)) ||
                moveCount > 1;
    }

}
