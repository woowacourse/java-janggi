package janggi.domain.piece.behavior;

import janggi.domain.Board;
import janggi.domain.Team;
import janggi.domain.move.Movement;
import janggi.domain.move.Position;
import janggi.domain.piece.PieceBehavior;
import janggi.domain.piece.PieceType;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class Soldier implements PieceBehavior {

    private static final Set<Movement> STANDARD_MOVEMENTS = Set.of(Movement.DOWN, Movement.LEFT, Movement.RIGHT);

    private static final Set<Movement> CROSS_MOVEMENTS = Set.of(Movement.LEFT_DOWN, Movement.RIGHT_DOWN);

    @Override
    public Set<Position> generateAvailableMovePositions(Board board, Team team, Position position) {
        Set<Position> positions = getAvailableStandardMovePositions(board, position, team);

        if (position.canCrossMove()) {
            Set<Position> crossMovePositions = getAvailableCrossMovePositions(board, position, team);
            positions.addAll(crossMovePositions);
        }

        return positions;
    }

    @Override
    public String toName() {
        return PieceType.SOLDIER.getName();
    }

    @Override
    public int toScore() {
        return 2;
    }

    private Set<Position> getAvailableStandardMovePositions(Board board, Position position, Team team) {
        return STANDARD_MOVEMENTS.stream()
                .map(Movement::getVector)
                .map(vector -> position.getValidNextPosition(vector.side(team)))
                .flatMap(Optional::stream)
                .filter(availablePosition -> board.canMoveToPosition(team, availablePosition))
                .collect(Collectors.toSet());
    }

    private Set<Position> getAvailableCrossMovePositions(Board board, Position position, Team team) {
        return CROSS_MOVEMENTS.stream().map(Movement::getVector)
                .map(vector -> position.getValidNextPosition(vector.side(team)))
                .flatMap(Optional::stream)
                .filter(availablePosition -> board.canMoveToPosition(team, availablePosition))
                .filter(Position::isPalace)
                .collect(Collectors.toSet());
    }
}
