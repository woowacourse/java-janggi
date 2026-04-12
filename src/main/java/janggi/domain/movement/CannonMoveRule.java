package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.team.TeamType;
import java.util.List;

public class CannonMoveRule implements MoveRule {

    private static final int MAX_DISTANCE = 10;
    private final Movement movement;

    public CannonMoveRule(final Direction direction) {
        this.movement = new Movement(direction);
    }

    @Override
    public List<Position> execute(Position from, final TeamType teamType, final BoardMediator boardMediator) {
        from = movement.findFirstOccupiedPalacePositionOrMax(from, boardMediator);
        if (isInvalidBridge(from, boardMediator)) {
            return List.of();
        }
        return removeCannonFromPositions(movement.calculateTraces(from, teamType, boardMediator, MAX_DISTANCE),
                boardMediator);
    }

    private boolean isInvalidBridge(final Position bridge, final BoardMediator boardMediator) {
        return !boardMediator.hasPieceAt(bridge) || boardMediator.isCannon(bridge);
    }

    private List<Position> removeCannonFromPositions(final List<Position> movablePositions,
                                                     final BoardMediator boardMediator) {
        return movablePositions.stream()
                .filter(position -> !isCannonPosition(position, boardMediator))
                .toList();
    }

    private boolean isCannonPosition(final Position position, final BoardMediator boardMediator) {
        return boardMediator.hasPieceAt(position) && boardMediator.isCannon(position);
    }
}
