package janggi.domain.piece.movement.strategy;

public class HorseStrategy extends StraightThenDiagonalStrategy {

    private static final int DIAGONAL_COUNT = 1;

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
    protected void validateMovement(DirectionInformation directionInformation) {
        int absRowDifference = directionInformation.calculateAbsRowDifference();
        int absColumnDifference = directionInformation.calculateAbsColumnDifference();

        if ((absRowDifference != MIN_ABS_DELTA || absColumnDifference != MAX_ABS_DELTA)
                && (absRowDifference != MAX_ABS_DELTA || absColumnDifference != MIN_ABS_DELTA)) {
            throw new IllegalArgumentException(INVALID_HORSE_MOVE);
        }
    }

    @Override
    protected int getDiagonalCount() {
        return DIAGONAL_COUNT;
    }
}
