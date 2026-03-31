package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import java.util.List;

public abstract class SingleStepStraightStrategy implements MoveStrategy {

    public static final int SINGLE_STEP_DISTANCE = 1;
    private static final String INVALID_SINGLE_STEP_STRAIGHT_MOVE = String.format(
            "[ERROR] 해당 기물은 직선으로 %d칸 이동해야 합니다.",
            SINGLE_STEP_DISTANCE
    );

    @Override
    public List<Position> findPath(Position source, Position destination, Camp camp) {
        DirectionInformation directionInformation = new DirectionInformation(source, destination);

        if (camp.isPalace(source) && camp.isPalace(destination)) {
            validatePalaceSingleStepMovement(directionInformation);
            return List.of(destination);
        }

        validateSingleStepMovement(directionInformation);
        return List.of(destination);
    }

    private void validateSingleStepMovement(DirectionInformation directionInformation) {
        if (isNotSingleStep(directionInformation)) {
            throw new IllegalArgumentException(INVALID_SINGLE_STEP_STRAIGHT_MOVE);
        }
    }

    private void validatePalaceSingleStepMovement(DirectionInformation directionInformation) {
        if (isNotSingleDiagonalStep(directionInformation) && isNotSingleStep(directionInformation)) {
            throw new IllegalArgumentException(INVALID_SINGLE_STEP_STRAIGHT_MOVE);
        }
    }

    private boolean isNotSingleDiagonalStep(DirectionInformation directionInformation) {
        int absRowDifference = directionInformation.calculateAbsRowDifference();
        int absColumnDifference = directionInformation.calculateAbsColumnDifference();

        return absRowDifference != 1 || absColumnDifference != 1;
    }

    private boolean isNotSingleStep(DirectionInformation directionInformation) {
        return directionInformation.calculateAbsRowDifference()
                + directionInformation.calculateAbsColumnDifference() != SINGLE_STEP_DISTANCE;
    }
}
