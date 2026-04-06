package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.team.TeamType;
import java.util.List;

public class CannonPalaceMoveRule implements MoveRule {

    private final Movement movement;

    public CannonPalaceMoveRule(final Direction direction) {
        this.movement = new Movement(direction);
    }

    @Override
    public List<Position> execute(Position from, final TeamType teamType, final BoardMediator boardMediator) {
        from = movement.findFirstOccupiedPositionOrMax(from, boardMediator);  // 포다리 찾기
        if (isInvalidBridge(from, boardMediator)) {
            return List.of();
        }
        return removeCannonFromPositions(movement.calculateTracesForPalace(from, teamType, boardMediator),
                boardMediator);
    }

    // 포다리가 안되는 경우 검증(빈 공간인지 or 포다리가 포 인지)
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
