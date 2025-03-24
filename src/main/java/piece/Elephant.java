package piece;

import java.util.HashSet;
import java.util.Set;

import board.Board;
import board.Position;

public class Elephant extends Piece {

    public Elephant(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    protected Set<Position> getMovablePositions(final Board board) {
        Set<Position> positions = new HashSet<>();
        Direction.getStraightDirection().forEach(direction -> goOneSide(
                position.nextPosition(direction),
                direction,
                positions,
                0,
                board
        ));
        return positions;
    }

    @Override
    public String getDisplayName() {
        return "상";
    }

    void goOneSide(Position position, Direction direction, Set<Position> positions, int moveCount, Board board) {
        if (exitCondition(position, moveCount, board)) {
            return;
        }
        if (moveCount == 2 && !board.isSameTeam(this, position)) {
            positions.add(position);
        }
        if (direction.isStraightDirection()) {
            direction.nextCrossDirection().forEach(crossDirection ->
                    goOneSide(position.nextPosition(crossDirection), crossDirection, positions, moveCount + 1, board)
            );
            return;
        }
        goOneSide(position.nextPosition(direction), direction, positions, moveCount + 1, board);
    }

    private boolean exitCondition(Position position, int moveCount, Board board) {
        return (
                position.isInValidPosition() ||
                (moveCount != 2 && board.isExists(position)) ||
                moveCount > 2
        );
    }

}
