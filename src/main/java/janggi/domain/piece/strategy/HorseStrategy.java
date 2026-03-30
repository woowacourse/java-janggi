package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.exception.ExceptionMessage;
import java.util.ArrayList;
import java.util.List;

public class HorseStrategy implements MoveStrategy {

    private static final int MIN_ABS_DELTA = 1;
    private static final int MAX_ABS_DELTA = 2;

    public static final int HORSE_STRAIGHT_MOVE_DISTANCE = 1;
    public static final int HORSE_DIAGONAL_MOVE_DISTANCE = 1;

    @Override
    public List<Position> findPath(Position source, Position destination, Camp camp) {
        DirectionInformation directionInformation = new DirectionInformation(source, destination);

        validateHorseMovement(directionInformation);

        if (directionInformation.isRowBiggerThanColumn()) {
            return createRowFirstPath(source, directionInformation);
        }
        return createColumnFirstPath(source, directionInformation);
    }

    private List<Position> createRowFirstPath(Position source, DirectionInformation directionInformation) {
        List<Position> path = new ArrayList<>();

        int rowDirection = directionInformation.calculateRowDirection();
        int columnDirection = directionInformation.calculateColumnDirection();
        source = source.moveRow(rowDirection);
        path.add(source);

        source = source.moveDiagonal(rowDirection, columnDirection);
        path.add(source);
        return path;
    }

    private List<Position> createColumnFirstPath(Position source, DirectionInformation directionInformation) {
        List<Position> path = new ArrayList<>();

        int rowDirection = directionInformation.calculateRowDirection();
        int columnDirection = directionInformation.calculateColumnDirection();
        source = source.moveColumn(columnDirection);
        path.add(source);

        source = source.moveDiagonal(rowDirection, columnDirection);
        path.add(source);
        return path;
    }

    private void validateHorseMovement(DirectionInformation directionInformation) {
        int absRowDifference = directionInformation.calculateAbsRowDifference();
        int absColumnDifference = directionInformation.calculateAbsColumnDifference();

        if ((absRowDifference != MIN_ABS_DELTA || absColumnDifference != MAX_ABS_DELTA)
                && (absRowDifference != MAX_ABS_DELTA || absColumnDifference != MIN_ABS_DELTA)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_HORSE_MOVE.getMessage());
        }
    }
}
