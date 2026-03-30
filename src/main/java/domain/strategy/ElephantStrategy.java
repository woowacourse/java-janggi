package domain.strategy;

import domain.Position;
import domain.piece.PieceProvider;

import java.util.ArrayList;
import java.util.List;

public class ElephantStrategy implements MoveStrategy {

    @Override
    public List<Position> getMoveCandidates(Position currentPosition, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();
        Direction[] straightDirections = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

        for (Direction straight : straightDirections) {
            Position straightStepPosition = new Position(
                    currentPosition.getRows() + straight.getRowOffset(),
                    currentPosition.getColumns() + straight.getColOffset()
            );

            if (!board.isBlank(straightStepPosition)) continue;

            for (Direction diag : getDiagonalsFor(straight)) {
                Position diagonalStepPosition = new Position(
                        straightStepPosition.getRows() + diag.getRowOffset(),
                        straightStepPosition.getColumns() + diag.getColOffset()
                );

                if (!board.isBlank(diagonalStepPosition)) continue;

                Position target = new Position(diagonalStepPosition.getRows() + diag.getRowOffset(),
                        diagonalStepPosition.getColumns() + diag.getColOffset()
                );
                candidates.add(target);
            }
        }
        return candidates;
    }

    private List<Direction> getDiagonalsFor(Direction straight) {
        if (straight == Direction.NORTH) return List.of(Direction.NORTH_WEST, Direction.NORTH_EAST);
        if (straight == Direction.SOUTH) return List.of(Direction.SOUTH_WEST, Direction.SOUTH_EAST);
        if (straight == Direction.WEST) return List.of(Direction.NORTH_WEST, Direction.SOUTH_WEST);
        if (straight == Direction.EAST) return List.of(Direction.NORTH_EAST, Direction.SOUTH_EAST);
        return List.of();
    }
}
