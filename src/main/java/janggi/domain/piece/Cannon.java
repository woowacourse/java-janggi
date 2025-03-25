package janggi.domain.piece;

import janggi.domain.piece.movement.Movement;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Cannon extends Piece {
    public static final List<Position> INITIAL_POSITIONS_BLUE = List.of(
            new Position(8, 2),
            new Position(8, 8));
    public static final List<Position> INITIAL_POSITIONS_RED = List.of(
            new Position(3, 2),
            new Position(3, 8));

    public Cannon(final Team team) {
        super("포", team);
    }

    @Override
    public Consumer<Map<Position, Piece>> getMovableValidator(final Position beforePosition,
                                                              final Position afterPosition) {
        return board -> {
            validateNoSameTeamPieceAt(board, afterPosition);
            validateStraightMovement(beforePosition, afterPosition);
            validateDestinationNotCannon(board, afterPosition);
            validateSingleJumpOverPiece(board, beforePosition, afterPosition);
        };
    }

    private void validateStraightMovement(final Position beforePosition, final Position afterPosition) {
        if (!isStraightMovement(beforePosition, afterPosition)) {
            throw new IllegalArgumentException("불가능한 이동입니다.");
        }
    }

    private boolean isStraightMovement(final Position beforePosition, final Position afterPosition) {
        return afterPosition.x() == beforePosition.x() || afterPosition.y() == beforePosition.y();
    }

    private void validateDestinationNotCannon(Map<Position, Piece> board, Position afterPosition) {
        if (board.get(afterPosition).isCannon()) {
            throw new IllegalArgumentException("불가능한 이동입니다.");
        }
    }

    private void validateSingleJumpOverPiece(Map<Position, Piece> board, Position beforePosition, Position afterPosition) {
        Movement movement = Movement.findByRelativePosition(
                afterPosition.x() - beforePosition.x(),
                afterPosition.y() - beforePosition.y()
        );

        int obstaclesCount = 0;

        Position currentPosition = beforePosition.plus(movement.x(), movement.y());
        while (!currentPosition.equals(afterPosition)) {
            validateNoCannonOnPath(board, currentPosition);
            if (!board.get(currentPosition).isNone()) {
                obstaclesCount++;
            }

            if (obstaclesCount > 1) {
                throw new IllegalArgumentException("불가능한 이동입니다");
            }

            currentPosition = currentPosition.plus(movement.x(), movement.y());
        }

        if (obstaclesCount != 1) {
            throw new IllegalArgumentException("불가능한 이동입니다");
        }
    }

    private void validateNoCannonOnPath(final Map<Position, Piece> pieces, final Position position) {
        if (pieces.get(position).isCannon()) {
            throw new IllegalArgumentException("불가능한 이동입니다");
        }
    }

    @Override
    public boolean isCannon() {
        return true;
    }
}