package domain.strategy;

import domain.Position;
import domain.Team;
import domain.piece.PieceProvider;
import util.CollisionValidator;

import java.util.ArrayList;
import java.util.List;

public class PalaceStrategy implements MoveStrategy {

    private static final List<Direction> directions = List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST,
            Direction.NORTH_EAST, Direction.NORTH_WEST, Direction.SOUTH_EAST, Direction.SOUTH_WEST);

    @Override
    public List<Position> getMoveCandidates(Position currentPosition, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();
        Team team = board.getPiece(currentPosition).getTeam();
        for (Direction direction : directions) {
            int targetRow = currentPosition.getRows() + direction.getRowOffset();
            int targetColumns = currentPosition.getColumns() + direction.getColOffset();

            Position targetPosition = new Position(targetRow, targetColumns);
            if (CollisionValidator.canMoveToTarget(targetPosition, board, team)) {
                candidates.add(targetPosition);
            }
        }
        return candidates;
    }
}
