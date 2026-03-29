package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.exception.ExceptionMessage;
import java.util.ArrayList;
import java.util.List;

public class ElephantStrategy implements MoveStrategy {

    private static final int DIAGONAL_COUNT = 2;
    private static final int MIN_ABS_DELTA = 2;
    private static final int MAX_ABS_DELTA = 3;

    @Override
    public List<Position> findPath(Position source, Position destination, Camp camp) {
        DirectionInformation directionInfo = new DirectionInformation(source, destination);

        validateElephantMovement(directionInfo);

        if (directionInfo.isRowBiggerThanCol()) {
            return createRowFirstPath(source, directionInfo);
        }
        return createColFirstPath(source, directionInfo);
    }

    private void validateElephantMovement(DirectionInformation directionInfo) {
        if ((directionInfo.calculateAbsRowDifference() != MIN_ABS_DELTA
                || directionInfo.calculateAbsColDifference() != MAX_ABS_DELTA)
                && (directionInfo.calculateAbsRowDifference() != MAX_ABS_DELTA
                || directionInfo.calculateAbsColDifference() != MIN_ABS_DELTA)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_ELEPHANT_MOVE.getMessage());
        }
    }

    private List<Position> createRowFirstPath(Position source, DirectionInformation directionInfo) {
        List<Position> path = new ArrayList<>();

        Position current = source.moveRow(directionInfo.calculateRowDirection());
        path.add(current);

        path.addAll(moveDiagonal(current, directionInfo));
        return path;
    }

    private List<Position> createColFirstPath(Position source, DirectionInformation directionInfo) {
        List<Position> path = new ArrayList<>();

        Position current = source.moveCol(directionInfo.calculateColDirection());
        path.add(current);

        path.addAll(moveDiagonal(current, directionInfo));
        return path;
    }

    private List<Position> moveDiagonal(Position source, DirectionInformation directionInfo) {
        List<Position> path = new ArrayList<>();

        Position current = source;
        for (int i = 0; i < DIAGONAL_COUNT; i++) {
            current = current.moveDiagonal(directionInfo.calculateRowDirection(),
                    directionInfo.calculateColDirection());
            path.add(current);
        }
        return path;
    }
}
