package domain;

import java.util.List;

public interface MoveStrategy {
    boolean isMoveAble(Position position);

    boolean isRootBlockedBy(List<Position> piecePositions);
}
