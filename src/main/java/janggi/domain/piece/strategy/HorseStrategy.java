package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.exception.ExceptionMessage;
import java.util.ArrayList;
import java.util.List;

public class HorseStrategy implements MoveStrategy {

    private static final int MIN_ABS_DELTA = 1;
    private static final int MAX_ABS_DELTA = 2;

    @Override
    public List<Position> findPath(Position from, Position to, Camp camp) {
        DirectionInformation directionInformation = new DirectionInformation(from, to);

        validateHorseMovement(directionInformation);

        if (directionInformation.isRowBiggerThanCol()) {
            return createRowFirstPath(from, directionInformation);
        }
        return createColFirstPath(from, directionInformation);
    }

    private List<Position> createRowFirstPath(Position from, DirectionInformation directionInformation) {
        List<Position> path = new ArrayList<>();

        int rowDirection = directionInformation.calculateRowDirection();
        int colDirection = directionInformation.calculateColDirection();
        from = from.moveRow(rowDirection);
        path.add(from);

        from = from.moveDiagonal(rowDirection, colDirection);
        path.add(from);
        return path;
    }

    private List<Position> createColFirstPath(Position from, DirectionInformation directionInformation) {
        List<Position> path = new ArrayList<>();

        int rowDirection = directionInformation.calculateRowDirection();
        int colDirection = directionInformation.calculateColDirection();
        from = from.moveCol(colDirection);
        path.add(from);

        from = from.moveDiagonal(rowDirection, colDirection);
        path.add(from);
        return path;
    }

    private void validateHorseMovement(DirectionInformation directionInformation) {
        int absRowDifference = directionInformation.calculateAbsRowDifference();
        int absColDifference = directionInformation.calculateAbsColDifference();

        if ((absRowDifference != MIN_ABS_DELTA || absColDifference != MAX_ABS_DELTA)
                && (absRowDifference != MAX_ABS_DELTA || absColDifference != MIN_ABS_DELTA)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_HORSE_MOVE.getMessage());
        }
    }
}
