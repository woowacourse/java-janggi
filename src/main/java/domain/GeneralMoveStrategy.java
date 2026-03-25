package domain;

import java.util.List;

public class GeneralMoveStrategy implements MoveStrategy {

    private final Position position;

    private GeneralMoveStrategy(Position position) {
        this.position = position;
    }

    static GeneralMoveStrategy of(Position position) {
        return new GeneralMoveStrategy(position);
    }

    @Override
    public boolean isMoveAble(Position position) {
        return false;
    }

    @Override
    public boolean isRootBlockedBy(List<Position> piecePositions) {
        return false;
    }
}
