package janggi.domain.policy;

import janggi.domain.board.BoardInterface;
import janggi.domain.Position;
import janggi.domain.Side;

import java.util.ArrayList;
import java.util.List;

public class ClearPathPolicy implements RoutePolicy {
    @Override
    public boolean isMovable(List<Position> path, Side side, BoardInterface boardInterface) {
        List<Position> pathBeforeTarget = new ArrayList<>(path.subList(1, path.size()));
        Position target = pathBeforeTarget.removeLast();
        return isMovableFirst(pathBeforeTarget, boardInterface) && isMovableLast(target, side,boardInterface);
    }

    private boolean isMovableFirst(List<Position> path, BoardInterface boardInterface){
        return path.stream()
                .allMatch(boardInterface::isEmpty);
    }

    private boolean isMovableLast(Position position, Side side, BoardInterface boardInterface){
        return !boardInterface.isAlly(side, position);
    }
}
