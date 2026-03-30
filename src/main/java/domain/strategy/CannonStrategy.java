package domain.strategy;

import domain.position.Position;
import domain.piece.Cannon;
import domain.piece.PieceProvider;

import java.util.ArrayList;
import java.util.List;

public class CannonStrategy implements MoveStrategy{

    @Override
    public List<Position> getMoveCandidates(Position currentPosition, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();
        Direction[] straightDirections = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
        for (Direction direction : straightDirections) {
            addCannonCandidates(currentPosition, direction, board, candidates);
        }
        return candidates;
    }

    private void addCannonCandidates(Position currentPosition, Direction direction, PieceProvider board, List<Position> candidatesPosition) {
        Position bridge = findFirstPiece(currentPosition, direction, board);
        boolean isInstanceOfCannon = board.getPiece(bridge) instanceof Cannon;
        if (!isWithinBoard(bridge) || isInstanceOfCannon) {
            return;
        }
        collectTargets(bridge, direction, board, candidatesPosition);
    }

    private Position findFirstPiece(Position position, Direction direction, PieceProvider board) {
        Position nextPosition = getNext(position, direction);
        while (isWithinBoard(nextPosition) && board.isBlank(nextPosition)) {
            nextPosition = getNext(nextPosition, direction);
        }
        return nextPosition;
    }

    private Position getNext(Position position, Direction direction) {
        int nextRows = position.getRows() + direction.getRowOffset();
        int nextColumns = position.getColumns() + direction.getColOffset();
        return new Position(nextRows, nextColumns);
    }

    private void collectTargets(Position bridge, Direction direction, PieceProvider board, List<Position> candidates) {
        Position target = getNext(bridge, direction);
        while (isWithinBoard(target) && board.isBlank(target)) {
            candidates.add(target);
            target = getNext(target, direction);
        }
        boolean isInstanceOfCannon = board.getPiece(target) instanceof Cannon;
        if (isWithinBoard(target) && !isInstanceOfCannon) {
            candidates.add(target);
        }
    }

    private boolean isWithinBoard(Position position) {
        return position.getRows() >= 0 && position.getRows() < 10 &&
                position.getColumns() >= 0 && position.getColumns() < 9;
    }
}
