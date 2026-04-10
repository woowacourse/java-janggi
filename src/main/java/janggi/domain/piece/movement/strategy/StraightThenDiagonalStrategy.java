package janggi.domain.piece.movement.strategy;

import janggi.domain.board.Position;
import java.util.ArrayList;
import java.util.List;

public abstract class StraightThenDiagonalStrategy implements MoveStrategy {

    @Override
    public List<Position> findPath(Position source, Position destination) {
        DirectionInformation directionInformation = new DirectionInformation(source, destination);

        validateMovement(directionInformation);

        if (directionInformation.isRowBiggerThanColumn()) {
            return createRowFirstPath(source, directionInformation);
        }
        return createColumnFirstPath(source, directionInformation);
    }

    private List<Position> createRowFirstPath(Position source, DirectionInformation directionInformation) {
        List<Position> path = new ArrayList<>();

        Position current = source.moveRow(directionInformation.calculateRowDirection());
        path.add(current);

        path.addAll(moveDiagonal(current, directionInformation));
        return path;
    }

    private List<Position> createColumnFirstPath(Position source, DirectionInformation directionInformation) {
        List<Position> path = new ArrayList<>();

        Position current = source.moveColumn(directionInformation.calculateColumnDirection());
        path.add(current);

        path.addAll(moveDiagonal(current, directionInformation));
        return path;
    }

    private List<Position> moveDiagonal(Position source, DirectionInformation directionInfo) {
        List<Position> path = new ArrayList<>();

        Position current = source;
        for (int i = 0; i < getDiagonalCount(); i++) {
            current = current.moveDiagonal(directionInfo.calculateRowDirection(),
                    directionInfo.calculateColumnDirection());
            path.add(current);
        }
        return path;
    }

    protected abstract void validateMovement(DirectionInformation directionInformation);

    protected abstract int getDiagonalCount();
}
