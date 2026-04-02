package domain.strategy;

import domain.position.Position;
import domain.PieceProvider;

import java.util.ArrayList;
import java.util.List;

public class PawnStrategy implements Strategy {

    @Override
    public List<Position> getMoveCandidates(Position from, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();
        Direction[] directions = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

        for (Direction direction : directions) {
            int targetRow = from.row() + direction.getRowOffset();
            int targetColumns = from.col() + direction.getColOffset();

            Position targetPosition = new Position(targetRow, targetColumns);
            candidates.add(targetPosition);
        }
        return candidates;
    }
}
