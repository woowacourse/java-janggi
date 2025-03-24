package piece;

import java.util.HashSet;
import java.util.Set;

import board.Board;
import board.Position;

public class Chariot extends Piece {

    public Chariot(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public Set<Position> getMovablePositions(final Board board) {
        Set<Position> positions = new HashSet<>();
        Direction.getStraightDirection().forEach(direction -> goOneSide(
                position.moveByDirection(direction),
                direction,
                positions,
                board
        ));
        return positions;
    }

    @Override
    public String getDisplayName() {
        return "차";
    }

    private void goOneSide(Position position, Direction direction, Set<Position> positions, final Board board) {
        if (exitCondition(position, board)) {
            return;
        }
        if (!board.isExists(position)) {
            goOneSide(position.moveByDirection(direction), direction, positions, board);
        }
        positions.add(position);
    }

    private boolean exitCondition(Position position, final Board board) {
        return (
                position.isInValidPosition() ||
                        (board.isExists(position) && board.isSameTeamPosition(this.team, position))
        );
    }

}
