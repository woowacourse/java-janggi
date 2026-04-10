package janggi.domain.piece.strategy;

import janggi.domain.board.Palace;
import janggi.domain.board.Position;
import java.util.ArrayList;
import java.util.List;

public class MultiStepStraightStrategy implements MoveStrategy {

    private static final String ONLY_STRAIGHT_MOVE_ALLOWED = "[ERROR] 해당 기물은 직선 이동만 가능합니다.";
    private static final String PIECE_MUST_MOVE = "[ERROR] 기물은 반드시 이동해야 합니다.";
    private static final String INVALID_PALACE_DIAGONAL_STEP_MOVE =
            "[ERROR] 궁성 내에 대각선이 존재하지 않는 경로 입니다.";

    @Override
    public List<Position> findPath(Position source, Position destination) {
        DirectionInformation directionInformation = new DirectionInformation(source, destination);
        validatePieceMoved(directionInformation);

        if (isPalace(source, destination) && isDiagonalStep(directionInformation)) {
            return createPalaceDiagonalPath(source, directionInformation);
        }

        validateStraightMove(directionInformation);
        return createStraightPath(source, directionInformation);
    }

    private boolean isPalace(Position source, Position destination) {
        return Palace.isPalace(source) && Palace.isPalace(destination);
    }

    private boolean isDiagonalStep(DirectionInformation directionInformation) {
        int absRowDifference = directionInformation.calculateAbsRowDifference();
        int absColumnDifference = directionInformation.calculateAbsColumnDifference();

        return absRowDifference == absColumnDifference;
    }

    private List<Position> createPalaceDiagonalPath(Position source, DirectionInformation directionInformation) {
        List<Position> path = createDiagonalPath(source, directionInformation);
        validatePalaceDiagonalPath(source, path);
        return path;
    }

    private List<Position> createDiagonalPath(Position source, DirectionInformation directionInformation) {
        List<Position> path = new ArrayList<>();

        Position current = source;
        int rowDirection = directionInformation.calculateRowDirection();
        int columnDirection = directionInformation.calculateColumnDirection();
        int stepCount = directionInformation.calculateAbsRowDifference();

        for (int i = 0; i < stepCount; i++) {
            current = current.moveDiagonal(rowDirection, columnDirection);
            path.add(current);
        }
        return path;
    }

    private void validatePalaceDiagonalPath(Position source, List<Position> path) {
        boolean passesPalaceCenter =
                Palace.isPalaceCenter(source) || path.stream().anyMatch(Palace::isPalaceCenter);

        if (!passesPalaceCenter) {
            throw new IllegalArgumentException(INVALID_PALACE_DIAGONAL_STEP_MOVE);
        }
    }

    private void validateStraightMove(DirectionInformation directionInformation) {
        int sum = directionInformation.addAllDifference();
        int rowDifference = directionInformation.rowDifference();
        int columnDifference = directionInformation.columnDifference();

        if (sum != rowDifference && sum != columnDifference) {
            throw new IllegalArgumentException(ONLY_STRAIGHT_MOVE_ALLOWED);
        }
    }

    private void validatePieceMoved(DirectionInformation directionInformation) {
        int rowDifference = directionInformation.rowDifference();
        int columnDifference = directionInformation.columnDifference();
        if (rowDifference == 0 && columnDifference == 0) {
            throw new IllegalArgumentException(PIECE_MUST_MOVE);
        }
    }

    private List<Position> createStraightPath(Position source, DirectionInformation directionInformation) {
        if (directionInformation.rowDifference() != 0) {
            return createRowPath(source, directionInformation.rowDifference());
        }
        return createColumnPath(source, directionInformation.columnDifference());
    }

    private List<Position> createRowPath(Position source, int rowDifference) {
        List<Position> path = new ArrayList<>();

        Position current = source;
        int rowDirection = rowDifference / Math.abs(rowDifference);
        while (rowDifference != 0) {
            current = current.moveRow(rowDirection);
            path.add(current);
            rowDifference -= rowDirection;
        }
        return path;
    }

    private List<Position> createColumnPath(Position source, int columnDifference) {
        List<Position> path = new ArrayList<>();

        Position current = source;
        int columnDirection = columnDifference / Math.abs(columnDifference);
        while (columnDifference != 0) {
            current = current.moveColumn(columnDirection);
            path.add(current);
            columnDifference -= columnDirection;
        }
        return path;
    }
}
