package janggi.domain.policy;

import janggi.domain.BoardInterface;
import janggi.domain.Position;
import janggi.domain.Side;

import java.util.List;

public class ClearPathPolicy implements RoutePolicy {
    @Override
    public boolean isMovable(List<Position> path, Side side, BoardInterface boardInterface) {
        return isMovableFirst(path, boardInterface) && isMovableLast(path.getLast(),side,boardInterface);
    }

    private boolean isMovableFirst(List<Position> path, BoardInterface boardInterface){
        return path.subList(0, path.size() - 1)
                .stream()
                .allMatch(boardInterface::isEmpty);
    }

    private boolean isMovableLast(Position position, Side side, BoardInterface boardInterface){
        return boardInterface.isEnemy(side, position) || boardInterface.isEmpty(position);
    }
}
