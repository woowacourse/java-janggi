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

    private void addPathCandidates(Position currentPosition, Direction direction, PieceProvider board, List<Position> candidatePositions) {
        Position next = currentPosition;

        while (true) {
            int nextRows = next.getRows() + direction.getRowOffset();
            int nextColumns = next.getColumns() + direction.getColOffset();

            if (nextRows < 0 || nextRows >= 10 || nextColumns < 0 || nextColumns >= 9) {
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
