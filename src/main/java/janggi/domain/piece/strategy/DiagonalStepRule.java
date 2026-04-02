package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceRule;
import janggi.exception.ExceptionMessage;
import java.util.ArrayList;
import java.util.List;

public class DiagonalStepRule extends BaseMoveRule {

    private static final int STRAIGHT_DISTANCE = 1;

    private final int diagonalDistance;

    public DiagonalStepRule(int diagonalDistance) {
        this.diagonalDistance = diagonalDistance;
    }

    @Override
    public void validate(Position source, Position destination, Camp camp, BoardChecker board, PieceRule pieceRule) {
        DirectionInformation direction = new DirectionInformation(source, destination);
        validateDistance(direction);
        List<Position> path = findPath(source, direction);
        validateEmptyPath(path, board);
    }

    private void validateDistance(DirectionInformation direction) {
        if (!direction.hasAbsDifferences(diagonalDistance, STRAIGHT_DISTANCE + diagonalDistance)) {
            throw new IllegalArgumentException(
                    ExceptionMessage.INVALID_DIAGONAL_STEP_MOVE.getMessage(STRAIGHT_DISTANCE, diagonalDistance)
            );
        }
    }

    private List<Position> findPath(Position source, DirectionInformation direction) {
        List<Position> path = new ArrayList<>();

        Position first = moveStraightStep(source, direction);
        path.add(first);
        path.addAll(moveDiagonal(first, direction));
        return path;
    }

    private Position moveStraightStep(Position source, DirectionInformation direction) {
        if (direction.isRowBiggerThanCol()) {
            return source.moveRow(direction.calculateRowDirection());
        }
        return source.moveCol(direction.calculateColDirection());
    }

    private List<Position> moveDiagonal(Position source, DirectionInformation direction) {
        List<Position> path = new ArrayList<>();
        int rowDirection = direction.calculateRowDirection();
        int colDirection = direction.calculateColDirection();

        for (int i = 0; i < diagonalDistance; i++) {
            source = source.moveDiagonal(rowDirection, colDirection);
            path.add(source);
        }
        return path;
    }
}
