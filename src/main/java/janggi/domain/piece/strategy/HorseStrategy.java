package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.exception.ExceptionMessage;
import java.util.ArrayList;
import java.util.List;

public class HorseStrategy implements MoveStrategy {

    private static final int HORSE_STRAIGHT_MOVE_DISTANCE = 1;
    private static final int HORSE_DIAGONAL_MOVE_DISTANCE = 1;
    private static final int MIN_ABS_DELTA = 1;
    private static final int MAX_ABS_DELTA = 2;

    @Override
    public List<Position> findPath(Position source, Position destination, Camp camp) {
        DirectionInformation directionInformation = new DirectionInformation(source, destination);

        validateHorseMovement(directionInformation);

        if (directionInformation.isRowBiggerThanCol()) {
            return createRowFirstPath(source, directionInformation);
        }
        return createColFirstPath(source, directionInformation);
    }

    private List<Position> createRowFirstPath(Position source, DirectionInformation directionInformation) {
        List<Position> path = new ArrayList<>();

        int rowDirection = directionInformation.calculateRowDirection();
        int colDirection = directionInformation.calculateColDirection();
        source = source.moveRow(rowDirection);
        path.add(source);

        source = source.moveDiagonal(rowDirection, colDirection);
        path.add(source);
        return path;
    }

    private List<Position> createColFirstPath(Position source, DirectionInformation directionInformation) {
        List<Position> path = new ArrayList<>();

        int rowDirection = directionInformation.calculateRowDirection();
        int colDirection = directionInformation.calculateColDirection();
        source = source.moveCol(colDirection);
        path.add(source);

        source = source.moveDiagonal(rowDirection, colDirection);
        path.add(source);
        return path;
    }

    private void validateHorseMovement(DirectionInformation directionInformation) {
        int absRowDifference = directionInformation.calculateAbsRowDifference();
        int absColDifference = directionInformation.calculateAbsColDifference();

        boolean isValidHorseMove =
                (absRowDifference == MIN_ABS_DELTA && absColDifference == MAX_ABS_DELTA)
                        || (absRowDifference == MAX_ABS_DELTA && absColDifference == MIN_ABS_DELTA);

        if (!isValidHorseMove) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_HORSE_MOVE.getMessage(
                    HORSE_STRAIGHT_MOVE_DISTANCE,
                    HORSE_DIAGONAL_MOVE_DISTANCE
            ));
        }
    }
}
