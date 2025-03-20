package domain.piece;

import domain.Board;
import domain.Direction;
import domain.Position;
import domain.Team;
import java.util.HashSet;
import java.util.Set;

public class Elephant extends Piece {

    public Elephant(final Position position, final Team team, final Board board) {
        super(position, team, board);
    }

    @Override
    protected Set<Position> getMovablePositions() {
        Set<Position> positions = new HashSet<>();
        Direction.getStraightDirection().forEach(direction ->
                goOneSide(position.nextPosition(direction), direction, positions, 0));
        return positions;
    }

    void goOneSide(Position position, Direction direction, Set<Position> positions, int moveCount) {
        if (exitCondition(position, moveCount)) {
            return;
        }
        if (moveCount == 2 && !board.isSameTeam(this, position)) {
            positions.add(position);
        }
        if (direction.isStraightDirection()) {
            direction.nextCrossDirection().forEach(crossDirection ->
                    goOneSide(position.nextPosition(crossDirection), crossDirection, positions, moveCount + 1)
            );
            return;
        }
        goOneSide(position.nextPosition(direction), direction, positions, moveCount + 1);
    }

    private boolean exitCondition(Position position, int moveCount) {
        return position.isInValidPosition() || (moveCount != 2 && board.isExists(position)) || moveCount > 2;
    }

}
