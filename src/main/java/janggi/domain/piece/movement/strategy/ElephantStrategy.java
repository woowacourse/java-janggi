package janggi.domain.piece.movement.strategy;

import janggi.domain.board.Position;
import java.util.ArrayList;
import java.util.List;

public class ElephantStrategy implements MoveStrategy {

    private static final int DIAGONAL_COUNT = 2;
    private static final int MIN_ABS_DELTA = 2;
    private static final int MAX_ABS_DELTA = 3;
    private static final int ELEPHANT_STRAIGHT_MOVE_DISTANCE = 1;
    private static final int ELEPHANT_DIAGONAL_MOVE_DISTANCE = 2;
    private static final String INVALID_ELEPHANT_MOVE = String.format(
            "[ERROR] 해당 기물은 직선 %d칸 이동 후 대각선 %d칸 이동만 가능합니다.",
            ELEPHANT_STRAIGHT_MOVE_DISTANCE,
            ELEPHANT_DIAGONAL_MOVE_DISTANCE
    );

    @Override
    public List<Position> findPath(Position source, Position destination) {
        DirectionInformation directionInfo = new DirectionInformation(source, destination);

        validateElephantMovement(directionInfo);

        if (directionInfo.isRowBiggerThanColumn()) {
            return createRowFirstPath(source, directionInfo);
        }
        return createColumnFirstPath(source, directionInfo);
    }

    private void validateElephantMovement(DirectionInformation directionInfo) {
        if ((directionInfo.calculateAbsRowDifference() != MIN_ABS_DELTA
                || directionInfo.calculateAbsColumnDifference() != MAX_ABS_DELTA)
                && (directionInfo.calculateAbsRowDifference() != MAX_ABS_DELTA
                || directionInfo.calculateAbsColumnDifference() != MIN_ABS_DELTA)) {
            throw new IllegalArgumentException(INVALID_ELEPHANT_MOVE);
        }
    }

    private List<Position> createRowFirstPath(Position source, DirectionInformation directionInfo) {
        List<Position> path = new ArrayList<>();

        Position current = source.moveRow(directionInfo.calculateRowDirection());
        path.add(current);

        path.addAll(moveDiagonal(current, directionInfo));
        return path;
    }

    private List<Position> createColumnFirstPath(Position source, DirectionInformation directionInfo) {
        List<Position> path = new ArrayList<>();

        Position current = source.moveColumn(directionInfo.calculateColumnDirection());
        path.add(current);

        path.addAll(moveDiagonal(current, directionInfo));
        return path;
    }

    private List<Position> moveDiagonal(Position source, DirectionInformation directionInfo) {
        List<Position> path = new ArrayList<>();

        Position current = source;
        for (int i = 0; i < DIAGONAL_COUNT; i++) {
            current = current.moveDiagonal(directionInfo.calculateRowDirection(),
                    directionInfo.calculateColumnDirection());
            path.add(current);
        }
        return path;
    }
}
