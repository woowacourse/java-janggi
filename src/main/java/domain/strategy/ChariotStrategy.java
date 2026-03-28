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
        Position next = getNext(currentPosition, direction);

        while (isWithinBoard(next)) {
            candidatePositions.add(next);
            if (!board.isBlank(next)) {
                break;
            }
            next = getNext(next, direction); // 다음 칸으로 갱신
        }
    }

    private Position getNext(Position position, Direction direction) {
        return new Position(position.getRows() + direction.getRowOffset(), position.getColumns() + direction.getColOffset());
    }

    private boolean isWithinBoard(Position position) {
        return position.getRows() >= 0 && position.getRows() < 10 &&
                position.getColumns() >= 0 && position.getColumns() < 9;
    }
}
