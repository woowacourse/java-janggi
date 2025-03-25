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
    public Consumer<Map<Position, Piece>> getMovableValidator(final Position beforePosition, final Position afterPosition) {
        return board -> {
            validateIsSameTeamNotInPositionToMove(board, afterPosition);
            validateIsPositionMovable(beforePosition, afterPosition);
            validateNotCannonInPositionToMove(board, afterPosition);
            validateOneNotCannonBetweenPositionToMove(board, beforePosition, afterPosition);
        };
    }

    private void validateIsPositionMovable(final Position beforePosition, final Position afterPosition) {
        if (checkIsPositionNotDiagonal(beforePosition, afterPosition)) {
            throw new IllegalArgumentException("불가능한 이동입니다.");
        }
    }

    private boolean checkIsPositionNotDiagonal(final Position beforePosition, final Position afterPosition) {
        return Math.abs(afterPosition.x() - beforePosition.x()) != 0 && Math.abs(afterPosition.y() - beforePosition.y()) != 0;
    }

    private void validateNotCannonInPositionToMove(Map<Position, Piece> board, Position afterPosition) {
        if (board.get(afterPosition).isCannon()) {
            throw new IllegalArgumentException("불가능한 이동입니다.");
        }
    }

    private void validateOneNotCannonBetweenPositionToMove(Map<Position, Piece> board, Position beforePosition, Position afterPosition) {
        Movement movement = Movement.getDistance(
                afterPosition.x() - beforePosition.x(),
                afterPosition.y() - beforePosition.y()
        );
        int count = 0;
        for (Position position = beforePosition.plus(movement.x(), movement.y());
             !position.equals(afterPosition);
             position = position.plus(movement.x(), movement.y())
        ) {
            isCannonOnMovement(board, position);
            if (!board.get(position).isNone()) {
                count++;
            }
        }

        if (count != 1) {
            throw new IllegalArgumentException("불가능한 이동입니다");
        }
    }

    private static void isCannonOnMovement(final Map<Position, Piece> pieces, final Position position) {
        if (pieces.get(position).isCannon()) {
            throw new IllegalArgumentException("불가능한 이동입니다");
        }
    }

    @Override
    public boolean isCannon() {
        return true;
    }
}
