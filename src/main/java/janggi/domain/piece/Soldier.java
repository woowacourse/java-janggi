package janggi.domain.piece;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Soldier extends Piece {
    public static final List<Position> INITIAL_POSITIONS_BLUE = List.of(
            new Position(7, 1),
            new Position(7, 3),
            new Position(7, 5),
            new Position(7, 7),
            new Position(7, 9)
    );
    public static final List<Position> INITIAL_POSITIONS_RED = List.of(
            new Position(4, 1),
            new Position(4, 3),
            new Position(4, 5),
            new Position(4, 7),
            new Position(4, 9)
    );

    public Soldier(final Team team) {
        super("졸", team);
    }

    @Override
    public Consumer<Map<Position, Piece>> getMovableValidator(
            final Position beforePosition,
            final Position afterPosition) {
        return board -> {
            validateIsSameTeamNotInPositionToMove(board, afterPosition);
            validateMovementDirection(beforePosition, afterPosition);
            validateValidPositionMovement(beforePosition, afterPosition);
        };
    }

    private void validateMovementDirection(final Position beforePosition, final Position afterPosition) {
        if (team == Team.BLUE && afterPosition.x() - beforePosition.x() > 0) {
            throw new IllegalArgumentException("파란팀 졸은 뒤로 이동할 수 없습니다.");
        }
        if (team == Team.RED && afterPosition.x() - beforePosition.x() < 0) {
            throw new IllegalArgumentException("빨간팀 졸은 뒤로 이동할 수 없습니다.");
        }
    }

    private void validateValidPositionMovement(final Position beforePosition, final Position afterPosition) {
        if (!isSingleStepMovement(beforePosition, afterPosition)) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
    }

    private boolean isSingleStepMovement(final Position beforePosition, final Position afterPosition) {
        return Math.abs(afterPosition.x() - beforePosition.x()) + Math.abs(afterPosition.y() - beforePosition.y()) == 1;
    }
}