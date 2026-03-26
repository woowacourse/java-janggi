package domain.strategy;

import domain.Position;
import java.util.List;

public abstract class MoveStrategy {

    protected final Position position;

    MoveStrategy(Position position) {
        this.position = position;
    }

    public abstract boolean isMoveAble(Position position);

    public abstract boolean isValidPath(Position destination, List<Position> piecePositions);
}
