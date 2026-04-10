package janggi.domain.policy;

import janggi.domain.board.BaseBoard;
import janggi.domain.Position;
import janggi.domain.Side;

import java.util.ArrayList;
import java.util.List;

public class ClearPathPolicy implements RoutePolicy {
    @Override
    public boolean isMovable(List<Position> path, Side side, BaseBoard baseBoard) {
        List<Position> pathBeforeTarget = new ArrayList<>(path.subList(1, path.size()));
        Position target = pathBeforeTarget.removeLast();
        return isMovableFirst(pathBeforeTarget, baseBoard) && isMovableLast(target, side, baseBoard);
    }

    private boolean isMovableFirst(List<Position> path, BaseBoard baseBoard) {
        return path.stream()
                .allMatch(baseBoard::isEmpty);
    }

    private boolean isMovableLast(Position position, Side side, BaseBoard baseBoard) {
        return !baseBoard.isAlly(side, position);
    }
}
