package janggi.domain.piece.behavior.palace;

import janggi.domain.Board;
import janggi.domain.Team;
import janggi.domain.move.Movement;
import janggi.domain.move.Position;
import janggi.domain.piece.BoardPositionInfo;
import janggi.domain.piece.PieceBehavior;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class PalaceBehavior implements PieceBehavior {

    private static final Set<Movement> STANDARD_MOVEMENTS = Set.of(
            Movement.DOWN,
            Movement.LEFT,
            Movement.RIGHT,
            Movement.UP
    );

    private static final Set<Movement> CROSS_MOVEMENTS = Set.of(
            Movement.RIGHT_UP,
            Movement.RIGHT_DOWN,
            Movement.LEFT_UP,
            Movement.LEFT_DOWN
    );

    @Override
    public final Set<Position> generateAvailableMovePositions(BoardPositionInfo boardPositionInfo) {
        Board board = boardPositionInfo.board();
        Position currentPosition = boardPositionInfo.position();
        Team team = boardPositionInfo.team();

        return Movement.getAvailableMovements(currentPosition, STANDARD_MOVEMENTS, CROSS_MOVEMENTS).stream()
                .map(Movement::getVector)
                .map(vector -> currentPosition.getValidNextPosition(vector.side(team)))
                .flatMap(Optional::stream)
                .filter(availablePosition -> board.canMoveToPosition(team, availablePosition))
                .filter(Position::isPalace)
                .collect(Collectors.toUnmodifiableSet());
    }
}
