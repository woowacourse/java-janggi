package domain.strategy;

import domain.position.Position;
import domain.piece.PieceProvider;

import java.util.ArrayList;
import java.util.List;

public class PawnStrategy implements MoveStrategy {

    @Override
    public List<Position> getMoveCandidates(Position currentPosition, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();

        Direction[] directions = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

        for (Direction direction : directions) {
            int targetRow = currentPosition.getRows() + direction.getRowOffset();
            int targetColumns = currentPosition.getColumns() + direction.getColOffset();

            Position targetPosition = new Position(targetRow, targetColumns);
            candidates.add(targetPosition);
        }
        return candidates;
    }
}
