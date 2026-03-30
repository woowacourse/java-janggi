package domain.strategy;

import domain.Position;
import domain.piece.PieceProvider;

import java.util.ArrayList;
import java.util.List;

public class ChariotStrategy implements MoveStrategy {

    @Override
    public List<Position> getMoveCandidates(Position currentPosition, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();
        Direction[] straightDirections = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

        for (Direction direction : straightDirections) {
            addPathCandidates(currentPosition, direction, board, candidates);
        }

        return candidates;
    }

    private void addPathCandidates(Position currentPosition, Direction direction, PieceProvider board, List<Position> candidates) {
        int nextRows = currentPosition.getRows() + direction.getRowOffset();
        int nextColumns = currentPosition.getColumns() + direction.getColOffset();

        while (isWithinBoard(nextRows, nextColumns)) {
            Position nextPosition = new Position(nextRows, nextColumns);
            candidates.add(nextPosition);
            if (!board.isBlank(nextPosition)) {
                break;
            }
            nextRows += direction.getRowOffset();
            nextColumns += direction.getColOffset();
        }
    }

    private boolean isWithinBoard(int row, int column) {
        return row >= 0 && row < 10 && column >= 0 && column < 9;
    }
}
