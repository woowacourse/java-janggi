package janggi.domain.policy;

import janggi.domain.BoardInterface;
import janggi.domain.Position;
import janggi.domain.Side;

import java.util.ArrayList;
import java.util.List;

public class JumpPolicy implements RoutePolicy {
    @Override
    public boolean isMovable(List<Position> path, Side side, BoardInterface boardInterface) {
        boolean hasPo = path.stream()
                .anyMatch(boardInterface::isPo);
        if (hasPo) {
            return false;
        }

        List<Position> pathBeforeTarget = new ArrayList<>(path);
        Position target = pathBeforeTarget.removeLast();

        return isMovableFirst(pathBeforeTarget, boardInterface) && isMovableLast(target, side, boardInterface);
    }

    private boolean isMovableFirst(List<Position> path, BoardInterface boardInterface) {
        List<Position> piecesOnPath = path.stream()
                .filter(position -> !boardInterface.isEmpty(position))
                .toList();

        return piecesOnPath.size() == 1;
    }

    private boolean isMovableLast(Position position, Side side, BoardInterface boardInterface) {
        return !boardInterface.isAlly(side, position);
    }
}
