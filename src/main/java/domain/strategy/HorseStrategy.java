package domain.strategy;

import domain.Position;
import domain.piece.PieceProvider;

import java.util.ArrayList;
import java.util.List;

public class HorseStrategy implements MoveStrategy {
    @Override
    public List<Position> getMoveCandidates(Position currentPosition, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();

        Direction[] straightDirections = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

        for (Direction straight : straightDirections) {
            int myeokRow = currentPosition.getRows() + straight.getRowOffset();
            int myeokCol = currentPosition.getColumns() + straight.getColOffset();
            Position myeokPosition = new Position(myeokRow, myeokCol);

            if (board.isBlank(myeokPosition)) {
                List<Direction> diagonals = getDiagonalsFor(straight);
                for (Direction diag : diagonals) {
                    int targetRow = myeokPosition.getRows() + diag.getRowOffset();
                    int targetCol = myeokPosition.getColumns() + diag.getColOffset();
                    Position targetPosition = new Position(targetRow, targetCol);

                    candidates.add(targetPosition);
                }
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
