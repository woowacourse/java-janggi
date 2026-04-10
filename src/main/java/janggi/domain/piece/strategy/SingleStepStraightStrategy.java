package janggi.domain.piece.strategy;

import janggi.domain.board.Palace;
import janggi.domain.board.Position;
import java.util.List;

public abstract class SingleStepStraightStrategy implements MoveStrategy {

    private static final int SINGLE_STEP_DISTANCE = 1;
    private static final String INVALID_SINGLE_STEP_STRAIGHT_MOVE = String.format(
            "[ERROR] 해당 기물은 직선으로 %d칸 이동해야 합니다.",
            SINGLE_STEP_DISTANCE
    );
    private static final String INVALID_PALACE_SINGLE_STEP_MOVE = String.format(
            "[ERROR] 해당 기물은 궁성 내에서 연결된 %d칸만 이동할 수 있습니다.",
            SINGLE_STEP_DISTANCE
    );
    private static final String INVALID_PALACE_DIAGONAL_STEP_MOVE =
            "[ERROR] 궁성 내에 대각선이 존재하지 않는 경로 입니다.";

    @Override
    public List<Position> findPath(Position source, Position destination) {
        DirectionInformation directionInformation = new DirectionInformation(source, destination);

        if (isPalace(source, destination)) {
            validatePalaceSingleStepMovement(source, destination, directionInformation);
            return List.of(destination);
        }

        validateSingleStepMovement(directionInformation);
        return List.of(destination);
    }

    private boolean isPalace(Position source, Position destination) {
        return Palace.isPalace(source) && Palace.isPalace(destination);
    }

    private void validatePalaceSingleStepMovement(Position source, Position destination,
                                                  DirectionInformation directionInformation) {
        if (isSingleDiagonalStep(directionInformation)) {
            validateDiagonalMove(source, destination);
        }
        if (!isSingleDiagonalStep(directionInformation) && !isSingleStep(directionInformation)) {
            throw new IllegalArgumentException(INVALID_PALACE_SINGLE_STEP_MOVE);
        }
    }

    private boolean isSingleDiagonalStep(DirectionInformation directionInformation) {
        int absRowDifference = directionInformation.calculateAbsRowDifference();
        int absColumnDifference = directionInformation.calculateAbsColumnDifference();

        return absRowDifference == 1 && absColumnDifference == 1;
    }

    private void validateDiagonalMove(Position source, Position destination) {
        if (!Palace.isPalaceCenter(source) && !Palace.isPalaceCenter(destination)) {
            throw new IllegalArgumentException(INVALID_PALACE_DIAGONAL_STEP_MOVE);
        }
    }

    private boolean isSingleStep(DirectionInformation directionInformation) {
        return directionInformation.calculateAbsRowDifference()
                + directionInformation.calculateAbsColumnDifference() == SINGLE_STEP_DISTANCE;
    }

    private void validateSingleStepMovement(DirectionInformation directionInformation) {
        if (!isSingleStep(directionInformation)) {
            throw new IllegalArgumentException(INVALID_SINGLE_STEP_STRAIGHT_MOVE);
        }
    }
}
