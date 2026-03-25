package domain;

import java.util.List;

public abstract class MoveStrategy {

    protected final Position position;

    MoveStrategy(Position position) {
        this.position = position;
    }

    abstract boolean isMoveAble(Position position);

    abstract boolean isRouteBlockedBy(List<Position> piecePositions);
}
