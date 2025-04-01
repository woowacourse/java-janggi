package janggi.domain.piece.behavior;

import janggi.domain.Board;
import janggi.domain.Team;
import janggi.domain.move.Movement;
import janggi.domain.move.Position;
import janggi.domain.piece.BoardPositionInfo;
import janggi.domain.piece.PieceBehavior;
import janggi.domain.piece.PieceType;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class Soldier implements PieceBehavior {

    private static final Set<Movement> STANDARD_MOVEMENTS = Set.of(Movement.DOWN, Movement.LEFT, Movement.RIGHT);

    private static final Set<Movement> CROSS_MOVEMENTS = Set.of(Movement.LEFT_DOWN, Movement.RIGHT_DOWN);

    @Override
    public Set<Position> generateAvailableMovePositions(BoardPositionInfo boardPositionInfo) {
        Position position = boardPositionInfo.position();

        Set<Position> positions = getAvailableStandardMovePositions(boardPositionInfo);

        if (position.canCrossMove()) {
            Set<Position> crossMovePositions = getAvailableCrossMovePositions(boardPositionInfo);
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
        return PieceType.SOLDIER.getScore();
    }

    private Set<Position> getAvailableStandardMovePositions(BoardPositionInfo boardPositionInfo) {
        return getAvailableMovePositions(boardPositionInfo, STANDARD_MOVEMENTS, p -> true);
    }

    private Set<Position> getAvailableCrossMovePositions(BoardPositionInfo boardPositionInfo) {
        return getAvailableMovePositions(boardPositionInfo, CROSS_MOVEMENTS, Position::isPalace);
    }

    private Set<Position> getAvailableMovePositions(BoardPositionInfo boardPositionInfo, Set<Movement> movements,
                                                    Predicate<Position> positionCondition) {
        Board board = boardPositionInfo.board();
        Position position = boardPositionInfo.position();
        Team team = boardPositionInfo.team();

        return movements.stream()
                .map(Movement::getVector)
                .map(vector -> position.getValidNextPosition(vector.side(team)))
                .flatMap(Optional::stream)
                .filter(availablePosition -> board.canMoveToPosition(team, availablePosition))
                .filter(positionCondition)
                .collect(Collectors.toSet());
    }
}
