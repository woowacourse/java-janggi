package janggi.domain.piece;

import janggi.domain.piece.movement.Movement;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Chariot extends Piece {
    public static final List<Position> INITIAL_POSITIONS_BLUE = List.of(
            new Position(10, 1),
            new Position(10, 9));
    public static final List<Position> INITIAL_POSITIONS_RED = List.of(
            new Position(1, 1),
            new Position(1, 9));

    public Chariot(final Team team) {
        super("차", team);
    }

    @Override
    public Consumer<Map<Position, Piece>> getMovableValidator(final Position beforePosition,
                                                              final Position afterPosition) {
        return board -> {
            validateNoSameTeamPieceAt(board, afterPosition);
            validateStraightMovement(beforePosition, afterPosition);
            validateNoObstaclesOnPath(board, beforePosition, afterPosition);
        };
    }

    private void validateStraightMovement(final Position beforePosition, final Position afterPosition) {
        if (!isStraightMovement(beforePosition, afterPosition)) {
            throw new IllegalArgumentException("불가능한 이동입니다.");
        }
    }

    private boolean isStraightMovement(final Position beforePosition, final Position afterPosition) {
        return beforePosition.x() == afterPosition.x() || beforePosition.y() == afterPosition.y();
    }

    private void validateNoObstaclesOnPath(
            Map<Position, Piece> board,
            Position beforePosition,
            Position afterPosition) {
        Movement movement = Movement.findByRelativePosition(
                afterPosition.x() - beforePosition.x(),
                afterPosition.y() - beforePosition.y()
        );

        Position currentPosition = beforePosition.plus(movement.x(), movement.y());
        while (!currentPosition.equals(afterPosition)) {
            if (!board.get(currentPosition).isNone()) {
                throw new IllegalArgumentException("불가능한 이동입니다");
            }
            currentPosition = currentPosition.plus(movement.x(), movement.y());
        }
    }
}