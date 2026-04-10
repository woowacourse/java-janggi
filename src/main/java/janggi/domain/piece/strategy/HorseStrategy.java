package janggi.domain.piece.strategy;

import janggi.domain.board.Position;
import java.util.ArrayList;
import java.util.List;

public class HorseStrategy implements MoveStrategy {

    private static final int MIN_ABS_DELTA = 1;
    private static final int MAX_ABS_DELTA = 2;
    private static final int HORSE_STRAIGHT_MOVE_DISTANCE = 1;
    private static final int HORSE_DIAGONAL_MOVE_DISTANCE = 1;
    private static final String INVALID_HORSE_MOVE = String.format(
            "[ERROR] 해당 기물은 직선 %d칸 이동 후 대각선 %d칸 이동만 가능합니다.",
            HORSE_STRAIGHT_MOVE_DISTANCE,
            HORSE_DIAGONAL_MOVE_DISTANCE
    );

    @Override
    public List<Position> findPath(Position source, Position destination) {
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
        Position current = source.moveRow(rowDirection);
        path.add(current);

        current = current.moveDiagonal(rowDirection, columnDirection);
        path.add(current);
        return path;
    }

    private List<Position> createColumnFirstPath(Position source, DirectionInformation directionInformation) {
        List<Position> path = new ArrayList<>();

        int rowDirection = directionInformation.calculateRowDirection();
        int columnDirection = directionInformation.calculateColumnDirection();
        Position current = source.moveColumn(columnDirection);
        path.add(current);

        current = current.moveDiagonal(rowDirection, columnDirection);
        path.add(current);
        return path;
    }

    private void validateHorseMovement(DirectionInformation directionInformation) {
        int absRowDifference = directionInformation.calculateAbsRowDifference();
        int absColumnDifference = directionInformation.calculateAbsColumnDifference();

        if ((absRowDifference != MIN_ABS_DELTA || absColumnDifference != MAX_ABS_DELTA)
                && (absRowDifference != MAX_ABS_DELTA || absColumnDifference != MIN_ABS_DELTA)) {
            throw new IllegalArgumentException(INVALID_HORSE_MOVE);
        }
    }
}
