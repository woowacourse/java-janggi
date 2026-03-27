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
    public List<Position> findPath(Position from, Position to, Camp camp) {
        DirectionInformation directionInfo = new DirectionInformation(from, to);

        validateElephantMovement(directionInfo);

        if (directionInfo.isRowBiggerThanCol()) {
            return createRowFirstPath(from, directionInfo);
        }
        return createColFirstPath(from, directionInfo);
    }

    private void validateElephantMovement(DirectionInformation directionInfo) {
        if ((directionInfo.calculateAbsRowDifference() != MIN_ABS_DELTA
                || directionInfo.calculateAbsColDifference() != MAX_ABS_DELTA)
                && (directionInfo.calculateAbsRowDifference() != MAX_ABS_DELTA
                || directionInfo.calculateAbsColDifference() != MIN_ABS_DELTA)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_ELEPHANT_MOVE.getMessage());
        }
    }

    private List<Position> createRowFirstPath(Position from, DirectionInformation directionInfo) {
        List<Position> path = new ArrayList<>();

        from = from.moveRow(directionInfo.calculateRowDirection());
        path.add(from);

        path.addAll(moveDiagonal(from, directionInfo));
        return path;
    }

    private List<Position> createColFirstPath(Position from, DirectionInformation directionInfo) {
        List<Position> path = new ArrayList<>();

        from = from.moveCol(directionInfo.calculateColDirection());
        path.add(from);

        path.addAll(moveDiagonal(from, directionInfo));
        return path;
    }

    private List<Position> moveDiagonal(Position from, DirectionInformation directionInfo) {
        List<Position> path = new ArrayList<>();

        for (int i = 0; i < DIAGONAL_COUNT; i++) {
            from = from.moveDiagonal(directionInfo.calculateRowDirection(), directionInfo.calculateColDirection());
            path.add(from);
        }
        return path;
    }
}
