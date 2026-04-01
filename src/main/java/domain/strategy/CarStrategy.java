package domain.strategy;

import static domain.Index.BOARD_COLUMNS;
import static domain.Index.BOARD_ROWS;

import domain.position.Position;
import domain.PieceProvider;

import java.util.ArrayList;
import java.util.List;

public class CarStrategy implements Strategy {

    @Override
    public List<Position> getMoveCandidates(Position from, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();
        Direction[] straightDirections = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

        for (Direction direction : straightDirections) {
            addPathCandidates(from, direction, board, candidates);
        }

        return candidates;
    }

    private void addPathCandidates(Position currentPosition, Direction direction, PieceProvider board, List<Position> candidatePositions) {
        Position next = currentPosition;

        while (true) {
            int nextRows = next.getRow() + direction.getRowOffset();
            int nextColumns = next.getColumn() + direction.getColOffset();

            if (nextRows < 0 || nextRows >= BOARD_ROWS.getIndex() || nextColumns < 0 || nextColumns >= BOARD_COLUMNS.getIndex()) {
                break;
            }

            next = new Position(nextRows, nextColumns);
            if (board.isBlank(next)) {
                candidatePositions.add(next);
                continue;
            }
            candidatePositions.add(next);
            break;
        }
    }
}
