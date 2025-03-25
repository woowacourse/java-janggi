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
        Set<Position> movablePositions = new HashSet<>();
        Direction.getStraightDirection()
                .forEach(direction ->
                        addMovablePositionsInDirection(direction, board, movablePositions)
                );
        return movablePositions;
    }

    private void addMovablePositionsInDirection(final Direction direction, final Board board,
                                                final Set<Position> movablePositions
    ) {
        Position movePosition = this.position;
        while (true) {
            movePosition = movePosition.moveByDirection(direction);
            if (movePosition.isInValidPosition() || board.isSameTeamPosition(team, movePosition)) {
                break;
            }
            movablePositions.add(movePosition);
            if (board.isExists(movePosition)) {
                break;
            }
        }
    }

    @Override
    public String getDisplayName() {
        return "차";
    }

}
